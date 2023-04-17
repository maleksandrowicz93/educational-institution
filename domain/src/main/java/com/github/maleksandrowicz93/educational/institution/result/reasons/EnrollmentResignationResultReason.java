package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum EnrollmentResignationResultReason implements ResultReason {

    INCORRECT_STUDENT_ID("Incorrect student id."),
    INCORRECT_FACULTY_ID("Incorrect faculty id.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
