package com.github.maleksandrowicz93.educational.institution.common;

public interface EventsPublisher {

    void publish(DomainEvent event);
}
