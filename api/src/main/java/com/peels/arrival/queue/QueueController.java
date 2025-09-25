package com.peels.arrival.queue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peels.arrival.queue.model.VisitorQueue;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueResolver resolver;

    public QueueController(final QueueResolver resolver) {
        this.resolver = resolver;
    }

    @GetMapping
    public VisitorQueue getQueue() {
        return resolver.resolve();
    }

}
