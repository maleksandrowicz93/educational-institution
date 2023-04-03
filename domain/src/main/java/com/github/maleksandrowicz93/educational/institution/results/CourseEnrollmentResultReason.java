package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseEnrollmentResultReason implements ResultReason {

    SUCCESS("");

    String text;

    @Override
    public String text() {
        return text;
    }

}
