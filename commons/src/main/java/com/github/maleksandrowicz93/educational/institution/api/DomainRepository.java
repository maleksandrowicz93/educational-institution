package com.github.maleksandrowicz93.educational.institution.api;

import java.util.Optional;

public interface DomainRepository<T extends Aggregate<S, ID>, S extends Snapshot<ID>, ID> {

    boolean existsById(ID id);

    Optional<T> findById(ID id);

    void save(T s);
}
