package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseEnrollmentResultReason implements ResultReason {

    NO_VACANCY("No vacancy at the course."),
    INVALID_FACULTY("The course is from other faculty.");

    String text;

    @Override
    public String text() {
        return text;
    }

}
