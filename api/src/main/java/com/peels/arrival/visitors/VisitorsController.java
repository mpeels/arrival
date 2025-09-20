package com.peels.arrival.visitors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visitors")
public class VisitorsController {

    @GetMapping
    public String listVisitors() {
        return "List pending visitors";
    }

    @GetMapping("/all")
    public String listAllVisitors() {
        return "List all visitors";
    }

    @PostMapping
    public String addVisitor() {
        return "Add new visitor";
    }

    @PutMapping("/{id}")
    public String updateVisitor(@PathVariable("id") Long id) {
        return "Update visitor";
    }

    @DeleteMapping("/{id}")
    public String removeVisitor(@PathVariable("id") Long id) {
        return "Remove visitor";
    }
}
