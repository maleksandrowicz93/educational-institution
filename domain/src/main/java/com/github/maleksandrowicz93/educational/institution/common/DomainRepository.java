package com.github.maleksandrowicz93.educational.institution.common;

import java.util.Optional;

public interface DomainRepository<T, ID> {

    Optional<T> findById(ID id);

    void save(T e);
}
