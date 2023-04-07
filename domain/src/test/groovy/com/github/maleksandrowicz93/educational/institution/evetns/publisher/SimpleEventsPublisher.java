package com.github.maleksandrowicz93.educational.institution.evetns.publisher;

import com.github.maleksandrowicz93.educational.institution.common.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher;

import java.util.ArrayList;
import java.util.List;

public class SimpleEventsPublisher implements EventsPublisher {

    private final List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
    }

    @Override
    public List<DomainEvent> events() {
        return List.copyOf(events);
    }
}
