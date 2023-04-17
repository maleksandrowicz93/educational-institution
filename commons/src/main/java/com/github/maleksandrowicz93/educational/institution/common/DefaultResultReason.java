package com.github.maleksandrowicz93.educational.institution.common;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum DefaultResultReason implements ResultReason {

    SUCCESS("Success."),
    UNKNOWN_ERROR("Unknown error.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
