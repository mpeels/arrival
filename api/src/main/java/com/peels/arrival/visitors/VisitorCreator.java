package com.peels.arrival.visitors;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.peels.arrival.visitors.exceptions.InvalidNumberException;
import com.peels.arrival.visitors.exceptions.VisitorCreationException;
import com.peels.arrival.visitors.model.CreateVisitorRequest;
import com.peels.arrival.visitors.model.Visitor;

@Component
public class VisitorCreator {

    private final JdbcClient client;
    private final VisitorResolver resolver;

    public VisitorCreator(
            final JdbcClient client,
            final VisitorResolver resolver) {
        this.resolver = resolver;
        this.client = client;
    }

    private static final String INSERT_VISITOR = """
            INSERT INTO visitors
            (
                first_name,
                last_name,
                number,
                status
            )
            VALUES
            (
                :firstName,
                :lastName,
                :number,
                'WAITING'
            )
            """;

    private static final String SELECT_LATEST_NUMBER_PLUS_1 = """
            SELECT
                COALESCE(
                    (
                        SELECT
                            number + 1
                        FROM
                            visitors
                        ORDER BY
                            id DESC
                        LIMIT
                            1
                    ),
                    1
                );
             """;

    private static final String COUNT_PENDING_NUMBER_USES = """
            SELECT
                COUNT(*)
            FROM
                visitors
            WHERE
                number = :number
                AND status IN ('WAITING', 'IN_PROGRESS');
            """;

    /**
     * If a number is provided, ensure it is not already used by a visitor in the
     * WAITING, or IN_PROGRESS state.
     * If no number is provided, get the last assigned number + 1
     * 
     * @param providedNumber
     * @return int
     */
    private int validateNumber(Integer providedNumber) {
        if (providedNumber == null) {
            return client.sql(SELECT_LATEST_NUMBER_PLUS_1)
                    .query(Integer.class)
                    .single();
        }
        // Check if provided is already in a pending state
        Integer useCount = client.sql(COUNT_PENDING_NUMBER_USES)
                .param("number", providedNumber)
                .query(Integer.class)
                .single();
        if (useCount > 0) {
            throw new InvalidNumberException();
        }
        return providedNumber;
    }

    public Visitor create(CreateVisitorRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        client.sql(INSERT_VISITOR)
                .param("firstName", request.firstName())
                .param("lastName", request.lastName())
                .param("number", validateNumber(request.number()))
                .update(keyHolder, "id");

        return resolver.findById(keyHolder.getKeyAs(Long.class))
                .orElseThrow(VisitorCreationException::new);
    }

}
