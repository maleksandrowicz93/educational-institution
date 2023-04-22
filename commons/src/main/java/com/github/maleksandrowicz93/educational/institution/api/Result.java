package com.github.maleksandrowicz93.educational.institution.api;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.maleksandrowicz93.educational.institution.api.DefaultResultReason.*;
import static com.github.maleksandrowicz93.educational.institution.api.DefaultResultReason.SUCCESS;
import static com.github.maleksandrowicz93.educational.institution.api.DefaultResultReason.UNKNOWN_ERROR;

@FieldDefaults(makeFinal = true)
public class Result<T> {

    T value;
    @Getter
    ResultReason resultReason;
    Map<String, String> additionalProperties = new HashMap<>();

    protected Result(T value) {
        this.value = value;
        this.resultReason = SUCCESS;
    }

    protected Result(ResultReason resultReason) {
        this.value = null;
        this.resultReason = Optional.ofNullable(resultReason)
                .orElse(UNKNOWN_ERROR);
    }

    protected Result(ResultReason resultReason, Map<String, String> additionalProperties) {
        this.value = null;
        this.resultReason = Optional.ofNullable(resultReason)
                .orElse(UNKNOWN_ERROR);
        Optional.ofNullable(additionalProperties)
                .ifPresent(this.additionalProperties::putAll);
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value);
    }

    public static <T> Result<T> failure(ResultReason resultReason) {
        return new Result<>(resultReason);
    }

    public static <T> Result<T> failure(ResultReason resultReason, Map<String, String> additionalProperties) {
        return new Result<>(resultReason, additionalProperties);
    }

    public static <T> Result<T> notFound() {
        return new Result<>(NOT_FOUND);
    }


    public static FailureBuilder failureReason(ResultReason reason) {
        return new FailureBuilder(reason);
    }

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public Map<String, String> additionalProperties() {
        return Map.copyOf(additionalProperties);
    }

    @FieldDefaults(makeFinal = true)
    public static class FailureBuilder {

        ResultReason reason;
        Map<String, String> properties = new HashMap<>();

        public FailureBuilder(ResultReason reason) {
            this.reason = reason;
        }

        public FailureBuilder property(String name, String value) {
            properties.put(name, value);
            return this;
        }

        public <T> Result<T> build() {
            return new Result<>(reason, properties);
        }
    }
}
