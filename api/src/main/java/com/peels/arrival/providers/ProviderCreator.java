package com.peels.arrival.providers;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.peels.arrival.providers.exceptions.ProviderCreationException;
import com.peels.arrival.providers.model.CreateProviderRequest;
import com.peels.arrival.providers.model.Provider;

@Component
public class ProviderCreator {

    private final JdbcClient client;
    private final ProviderResolver resolver;

    public ProviderCreator(
            final JdbcClient client,
            final ProviderResolver resolver) {
        this.client = client;
        this.resolver = resolver;
    }

    private static final String INSERT_PROVIDER = """
            INSERT INTO providers (name)
            VALUES (:name);
            """;

    @Transactional
    public Provider create(CreateProviderRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        client.sql(INSERT_PROVIDER)
                .param("name", request.name())
                .update(keyHolder, "id");

        return resolver.findById(keyHolder.getKeyAs(Long.class))
                .orElseThrow(ProviderCreationException::new);
    }
}
