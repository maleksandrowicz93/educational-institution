package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum FacultyCreationResultReason implements ResultReason {

    SUCCESS("Success."),
    INCORRECT_EDUCATIONAL_INSTITUTION_ID("Invalid educational institution id.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
