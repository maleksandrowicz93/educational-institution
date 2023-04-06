package com.github.maleksandrowicz93.educational.institution.common;

import java.util.List;

public interface EventsPublisher {

    void publish(DomainEvent event);

    List<DomainEvent> events();
}
