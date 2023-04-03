package com.github.maleksandrowicz93.educational.institution.common;

public interface DomainRepository<T, ID> {

    T findById(ID id);

    void save(T e);
}
