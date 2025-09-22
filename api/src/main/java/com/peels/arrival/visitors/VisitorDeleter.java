package com.peels.arrival.visitors;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.peels.arrival.visitors.exceptions.VisitorUpdateException;
import com.peels.arrival.visitors.model.Visitor;

@Component
public class VisitorDeleter {

    private final JdbcClient client;
    private final VisitorResolver resolver;

    public VisitorDeleter(
            final JdbcClient client,
            final VisitorResolver resolver) {
        this.client = client;
        this.resolver = resolver;
    }

    private static final String SET_STATUS_DELETED = """
            UPDATE visitors
            SET
                status = 'DELETED',
                completed_time = NOW()
            WHERE
                id = :id;
            """;

    public Visitor delete(long id) {
        int rowsAffected = client.sql(SET_STATUS_DELETED)
                .param("id", id)
                .update();
        if (rowsAffected == 0) {
            throw new VisitorUpdateException();
        }

        return resolver.findById(id).orElseThrow(VisitorUpdateException::new);
    }

}
