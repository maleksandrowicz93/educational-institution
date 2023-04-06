package com.github.maleksandrowicz93.educational.institution.common;

import java.util.Optional;

public interface DomainRepository<S extends Snapshot<ID>, ID> {

    boolean existsById(ID id);

    Optional<S> findById(ID id);

    void save(S s);
}
