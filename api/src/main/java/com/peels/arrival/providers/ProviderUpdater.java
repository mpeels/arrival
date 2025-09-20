package com.peels.arrival.providers;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.peels.arrival.providers.model.Provider;
import com.peels.arrival.providers.model.UpdateProviderRequest;

@Component
public class ProviderUpdater {
    private final JdbcClient client;
    private final ProviderResolver resolver;

    public ProviderUpdater(
            final JdbcClient client,
            final ProviderResolver resolver) {
        this.client = client;
        this.resolver = resolver;
    }

    private static final String UPDATE_PROVIDER = """
            UPDATE providers SET name = :name, active = :active WHERE id = :id;
            """;

    @Transactional
    public Provider update(long id, UpdateProviderRequest request) {
        client.sql(UPDATE_PROVIDER)
                .param("name", request.name())
                .param("active", request.active())
                .param("id", id)
                .update();

        return resolver.findById(id);
    }

}
