package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum EnrollmentResignationResultReason implements ResultReason {

    SUCCESS("Success."),
    INCORRECT_STUDENT_ID("Incorrect student id."),
    INCORRECT_FACULTY_ID("Incorrect faculty id.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
