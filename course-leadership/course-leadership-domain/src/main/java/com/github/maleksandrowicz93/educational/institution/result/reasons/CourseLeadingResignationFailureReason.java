package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseLeadingResignationFailureReason implements ResultReason {

    COURSE_ALREADY_FREE("Course is already free.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
