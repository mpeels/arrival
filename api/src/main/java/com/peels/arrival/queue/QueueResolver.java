package com.peels.arrival.queue;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.peels.arrival.queue.model.VisitorQueue;
import com.peels.arrival.queue.model.VisitorQueue.QueueEntry;

@Component
public class QueueResolver {

    private final JdbcClient client;

    public QueueResolver(final JdbcClient client) {
        this.client = client;
    }

    private static final String SELECT_QUEUE = """
            SELECT
                v.first_name,
                v.last_name,
                v.number,
                p.name
            FROM visitors v JOIN providers p on v.provider = p.id
            WHERE v.status = 'IN_PROGRESS';
            """;

    private static final RowMapper<QueueEntry> rowMapper = (rs, row) -> new QueueEntry(
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("number"),
            rs.getString("name"));

    public VisitorQueue resolve() {
        return new VisitorQueue(
                client.sql(SELECT_QUEUE)
                        .query(rowMapper)
                        .list());
    }

}
