package com.peels.arrival.providers;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.peels.arrival.providers.model.Provider;

@Component
public class ProviderResolver {

    private final JdbcClient client;

    public ProviderResolver(final JdbcClient client) {
        this.client = client;
    }

    private static final String SELECT_ACTIVE = """
            SELECT id, name, active FROM providers WHERE active = TRUE;
            """;

    private static final String SELECT_ALL = """
            SELECT id, name, active FROM providers;
            """;

    private static final String SELECT_BY_ID = """
            SELECT id, name, active FROM providers WHERE id = :id;
            """;

    private static final RowMapper<Provider> rowMapper = (rs, row) -> new Provider(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getBoolean("active"));

    public List<Provider> findActive() {
        return client.sql(SELECT_ACTIVE)
                .query(rowMapper)
                .list();
    }

    public List<Provider> findAll() {
        return client.sql(SELECT_ALL)
                .query(rowMapper)
                .list();
    }

    public Optional<Provider> findById(long id) {
        return client.sql(SELECT_BY_ID)
                .param("id", id)
                .query(rowMapper)
                .optional();

    }

}
