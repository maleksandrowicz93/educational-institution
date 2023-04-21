package com.github.maleksandrowicz93.educational.institution.api;

public interface Entity<S extends Snapshot<ID>, ID> {

    ID id();

    S createSnapshot();
}
