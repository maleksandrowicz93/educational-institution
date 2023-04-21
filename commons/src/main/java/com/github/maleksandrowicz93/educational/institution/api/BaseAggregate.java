package com.github.maleksandrowicz93.educational.institution.api;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@SuperBuilder
@FieldDefaults(makeFinal = true)
public abstract class BaseAggregate<S extends Snapshot<ID>, ID> implements Aggregate<S, ID> {

    @Getter(PROTECTED)
    S source;
}
