package com.peels.arrival.visitors;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.peels.arrival.visitors.model.Visitor;

@Component
public class VisitorResolver {

    private final JdbcClient client;

    public VisitorResolver(final JdbcClient client) {
        this.client = client;
    }

    private static final String SELECT_PENDING = """
            SELECT
                id,
                first_name,
                last_name,
                number,
                status,
                provider,
                arrival_time,
                call_time,
                completed_time
            FROM
                visitors
            WHERE
                status IN ('WAITING', 'IN_PROGRESS')
            ORDER BY
                id ASC;
                        """;

    private static final String SELECT_ALL = """
            SELECT
                id,
                first_name,
                last_name,
                number,
                status,
                provider,
                arrival_time,
                call_time,
                completed_time
            FROM
                visitors
            ORDER BY
                id ASC;
                        """;

    private static final String SELECT_BY_ID = """
            SELECT
                id,
                first_name,
                last_name,
                number,
                status,
                provider,
                arrival_time,
                call_time,
                completed_time
            FROM
                visitors
            WHERE
                id = :id;
                        """;

    private static LocalDateTime toNullableLocalDateTime(Timestamp timestamp) {
        return Optional.ofNullable(timestamp).map(Timestamp::toLocalDateTime).orElseGet(() -> null);
    }

    // ResultSet returns 0 for null entries. We want to transform that 0 into a null
    private static Long toNullableLong(long value) {
        return value == 0l ? null : value;
    }

    private static final RowMapper<Visitor> rowMapper = (rs, row) -> new Visitor(
            rs.getLong("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("number"),
            Visitor.Status.valueOf(rs.getString("status")),
            toNullableLong(rs.getLong("provider")),
            rs.getTimestamp("arrival_time").toLocalDateTime(),
            toNullableLocalDateTime(rs.getTimestamp("call_time")),
            toNullableLocalDateTime(rs.getTimestamp("completed_time")));

    public List<Visitor> findPending() {
        return client.sql(SELECT_PENDING)
                .query(rowMapper)
                .list();
    }

    public List<Visitor> findAll() {
        return client.sql(SELECT_ALL)
                .query(rowMapper)
                .list();
    }

    public Optional<Visitor> findById(long id) {
        return client.sql(SELECT_BY_ID)
                .param("id", id)
                .query(rowMapper)
                .optional();
    }

}
