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

    private static final String SELECT_QUEUES = """
            SELECT
                v.first_name,
                v.last_name,
                v.number,
                p.name
            FROM providers p LEFT JOIN visitors v on v.provider = p.id
            WHERE
                p.active = true
            ORDER BY p.id asc
            ;
            """;

    // ResultSet returns 0 for null entries. We want to transform that 0 into a null
    private static Integer toNullableInt(int value) {
        return value == 0 ? null : value;
    }

    private static final RowMapper<QueueEntry> rowMapper = (rs, row) -> new QueueEntry(
            rs.getString("first_name"),
            rs.getString("last_name"),
            toNullableInt(rs.getInt("number")),
            rs.getString("name"));

    public VisitorQueue resolve() {
        return new VisitorQueue(
                client.sql(SELECT_QUEUES)
                        .query(rowMapper)
                        .list());
    }

}
