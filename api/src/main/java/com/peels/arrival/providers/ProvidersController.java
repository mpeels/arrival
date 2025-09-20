package com.peels.arrival.providers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peels.arrival.providers.model.CreateProviderRequest;
import com.peels.arrival.providers.model.Provider;
import com.peels.arrival.providers.model.UpdateProviderRequest;

@RestController
@RequestMapping("/api/providers")
public class ProvidersController {

    private final ProviderResolver resolver;
    private final ProviderCreator creator;
    private final ProviderUpdater updater;

    public ProvidersController(
            final ProviderResolver resolver,
            final ProviderCreator creator,
            final ProviderUpdater updater) {
        this.resolver = resolver;
        this.creator = creator;
        this.updater = updater;
    }

    @GetMapping
    public List<Provider> getActiveProviders() {
        return resolver.findActive();
    }

    @GetMapping("/all")
    public List<Provider> getAllProviders() {
        return resolver.findAll();
    }

    @PostMapping()
    public Provider createProvider(@RequestBody CreateProviderRequest request) {
        return creator.create(request);
    }

    @PutMapping("/{id}")
    public Provider updateProvider(@PathVariable("id") Long id, @RequestBody UpdateProviderRequest request) {
        return updater.update(id, request);
    }
}
