package com.github.maleksandrowicz93.educational.institution.common;

public interface Entity<S extends Snapshot<ID>, ID> {

    S createSnapshot();
}
