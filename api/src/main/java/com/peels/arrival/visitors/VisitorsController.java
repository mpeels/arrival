package com.peels.arrival.visitors;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peels.arrival.visitors.model.CreateVisitorRequest;
import com.peels.arrival.visitors.model.UpdateVisitorRequest;
import com.peels.arrival.visitors.model.Visitor;

@RestController
@RequestMapping("/api/visitors")
public class VisitorsController {

    private final VisitorResolver resolver;
    private final VisitorCreator creator;
    private final VisitorUpdater updater;
    private final VisitorDeleter deleter;

    public VisitorsController(
            final VisitorResolver resolver,
            final VisitorCreator creator,
            final VisitorUpdater updater,
            final VisitorDeleter deleter) {
        this.resolver = resolver;
        this.creator = creator;
        this.updater = updater;
        this.deleter = deleter;
    }

    @GetMapping
    public List<Visitor> listVisitors() {
        return resolver.findPending();
    }

    @GetMapping("/all")
    public List<Visitor> listAllVisitors() {
        return resolver.findAll();
    }

    @PostMapping
    public Visitor addVisitor(@RequestBody CreateVisitorRequest request) {
        return creator.create(request);
    }

    @PutMapping("/{id}")
    public Visitor updateVisitor(@PathVariable("id") Long id, @RequestBody UpdateVisitorRequest request) {
        return updater.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Visitor removeVisitor(@PathVariable("id") Long id) {
        return deleter.delete(id);
    }
}
