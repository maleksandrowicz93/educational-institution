package com.github.maleksandrowicz93.educational.institution.common;

import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(makeFinal = true)
public abstract class InMemoryRepository<T extends Aggregate<S, ID>, S extends Snapshot<ID>, ID>
        implements DomainRepository<T, S, ID> {

    Map<ID, T> repository = new ConcurrentHashMap<>();

    @Override
    public boolean existsById(ID id) {
        return repository.containsKey(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public void save(T t) {
        Optional.ofNullable(t)
                .filter(aggregate -> aggregate.id() != null)
                .ifPresent(aggregate -> repository.put(aggregate.id(), aggregate));
    }
}
