package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum FacultyCreationResultReason implements ResultReason {

    FACULTY_ALREADY_EXISTS("Faculty already exists within the educational institution.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
