package com.github.maleksandrowicz93.educational.institution.common;

import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@FieldDefaults(makeFinal = true)
public abstract class SimpleRepository<S extends Snapshot<ID>, ID> implements DomainRepository<S, ID> {

    Map<ID, S> repository = new HashMap<>();

    @Override
    public boolean existsById(ID id) {
        return repository.containsKey(id);
    }

    @Override
    public Optional<S> findById(ID id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public void save(S s) {
        Optional.ofNullable(s)
                .filter(snapshot -> snapshot.id() != null)
                .ifPresent(snapshot -> repository.put(s.id(), s));
    }
}
