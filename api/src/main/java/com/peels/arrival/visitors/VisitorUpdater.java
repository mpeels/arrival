package com.peels.arrival.visitors;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.peels.arrival.providers.ProviderResolver;
import com.peels.arrival.providers.model.Provider;
import com.peels.arrival.visitors.exceptions.InvalidNumberException;
import com.peels.arrival.visitors.exceptions.InvalidProviderException;
import com.peels.arrival.visitors.exceptions.VisitorUpdateException;
import com.peels.arrival.visitors.model.UpdateVisitorRequest;
import com.peels.arrival.visitors.model.Visitor;

@Component
public class VisitorUpdater {

    private final JdbcClient client;
    private final VisitorResolver resolver;
    private final ProviderResolver providerResolver;

    public VisitorUpdater(
            final JdbcClient client,
            final VisitorResolver resolver,
            final ProviderResolver providerResolver) {
        this.client = client;
        this.resolver = resolver;
        this.providerResolver = providerResolver;
    }

    private static final String UPDATE_VISITOR = """
            UPDATE visitors
            SET
                first_name = :firstName,
                last_name = :lastName,
                number = :number,
                status = :status,
                provider = :provider
            WHERE
                id = :id;
            """;

    private static final String SET_VISITOR_CALL_TIME = """
            UPDATE visitors
            SET
                call_time = NOW(),
                completed_time = NULL
            WHERE
                id = :id;
            """;

    private static final String SET_VISITOR_COMPLETED_TIME = """
            UPDATE visitors
            SET
                completed_time = NOW(),
                call_time = COALESCE(call_time, NOW())
            WHERE
                id = :id;
            """;

    private static final String COUNT_PENDING_NUMBER_USES = """
            SELECT
                COUNT(*)
            FROM
                visitors
            WHERE
                number = :number
                AND id <> :id
                AND status IN ('WAITING', 'IN_PROGRESS');
            """;

    private static final String SELECT_CURRENT_NUMBER = """
            SELECT
                number
            FROM
                visitors
            WHERE
                id = :id;
            """;

    /**
     * If a number is provided, ensure it is not already used by another visitor in
     * the
     * WAITING, or IN_PROGRESS state.
     * If no number is provided, get the current number for the visitor
     * 
     * @param providedNumber
     * @return int
     */
    private int validateNumber(Integer providedNumber, long id) {
        if (providedNumber == null) {
            return client.sql(SELECT_CURRENT_NUMBER)
                    .query(Integer.class)
                    .single();
        }
        // Check if provided is already in a pending state
        Integer useCount = client.sql(COUNT_PENDING_NUMBER_USES)
                .param("number", providedNumber)
                .param("id", id)
                .query(Integer.class)
                .single();
        if (useCount > 0) {
            throw new InvalidNumberException();
        }
        return providedNumber;
    }

    /**
     * Ensure the specified provider is valid and active
     * 
     * @param providerId
     * @return
     */
    private void validateProvider(Long providerId) {
        if (providerId == null) {
            throw new InvalidProviderException();
        }

        Optional<Provider> provider = providerResolver.findById(providerId);
        if (provider.isEmpty() || !provider.get().active()) {
            throw new InvalidProviderException();
        }
    }

    public Visitor update(long id, UpdateVisitorRequest request) {
        // ensure specified provider exists and is active
        validateProvider(request.provider());

        // update the general columns for the visitor
        int rowsAffected = client.sql(UPDATE_VISITOR)
                .param("firstName", request.firstName())
                .param("lastName", request.lastName())
                .param("number", validateNumber(request.number(), id))
                .param("status", request.status().toString())
                .param("provider", request.provider())
                .param("id", id)
                .update();

        if (rowsAffected == 0) {
            throw new VisitorUpdateException();
        }

        if (Visitor.Status.IN_PROGRESS.equals(request.status())) {
            // status is being set to IN_PROGRESS. Update the call_time in the database
            client.sql(SET_VISITOR_CALL_TIME)
                    .param("id", id)
                    .update();
        } else if (Visitor.Status.COMPLETED.equals(request.status())) {
            // status is COMPLETED, Update completed_time
            client.sql(SET_VISITOR_COMPLETED_TIME)
                    .param("id", id)
                    .update();
        }

        return resolver.findById(id)
                .orElseThrow(VisitorUpdateException::new);
    }

}
