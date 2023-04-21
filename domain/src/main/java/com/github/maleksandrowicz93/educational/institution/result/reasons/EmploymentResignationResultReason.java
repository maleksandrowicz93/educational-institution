package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.api.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum EmploymentResignationResultReason implements ResultReason {

    INCORRECT_PROFESSOR_ID("Incorrect professor id."),
    INCORRECT_FACULTY_ID("Incorrect faculty id.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
