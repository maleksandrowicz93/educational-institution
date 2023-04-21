package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.api.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseEnrollmentFailureReason implements ResultReason {

    NO_VACANCY("No vacancy at the course."),
    ALREADY_ENROLLED("Student is already enrolled for the course.");

    String text;

    @Override
    public String text() {
        return text;
    }

}
