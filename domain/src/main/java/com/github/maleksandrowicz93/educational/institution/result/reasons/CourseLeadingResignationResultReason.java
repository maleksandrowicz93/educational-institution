package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseLeadingResignationResultReason implements ResultReason {

    INCORRECT_COURSE_ID("Incorrect course id.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
