package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseEnrollmentResultReason implements ResultReason {

    SUCCESS("Success."),
    NO_VACANCY("No vacancy at the course.");

    String text;

    @Override
    public String text() {
        return text;
    }

}