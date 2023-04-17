package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
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
