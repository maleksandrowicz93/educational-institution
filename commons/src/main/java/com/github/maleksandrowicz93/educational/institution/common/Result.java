package com.github.maleksandrowicz93.educational.institution.common;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@FieldDefaults(makeFinal = true)
public abstract class Result<T, R extends ResultReason> {

    T value;
    @Getter
    R resultReason;
    Map<String, String> additionalProperties = new HashMap<>();

    public Result(T value) {
        this.value = value;
        this.resultReason = defaultResultReason();
    }

    public Result(R resultReason) {
        this.value = null;
        this.resultReason = resultReason;
    }

    public Result(R resultReason, Map<String, String> additionalProperties) {
        this.value = null;
        this.resultReason = resultReason;
        this.additionalProperties.putAll(additionalProperties);
    }

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public Map<String, String> additionalProperties() {
        return Map.copyOf(additionalProperties);
    }

    protected abstract R defaultResultReason();
}
