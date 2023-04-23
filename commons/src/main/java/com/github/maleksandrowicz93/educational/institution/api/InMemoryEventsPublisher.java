package com.github.maleksandrowicz93.educational.institution.api;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventsPublisher implements EventsPublisher {

    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> events() {
        return List.copyOf(events);
    }
}
