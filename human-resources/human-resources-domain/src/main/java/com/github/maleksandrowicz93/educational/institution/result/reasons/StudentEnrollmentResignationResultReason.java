package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.api.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum StudentEnrollmentResignationResultReason implements ResultReason {

    STUDENT_NOT_ENROLLED("Student not enrolled at the faculty.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
