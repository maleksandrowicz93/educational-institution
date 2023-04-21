package com.github.maleksandrowicz93.educational.institution.api;

public interface EventsPublisher {

    void publish(DomainEvent event);
}
