package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.api.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseClosingResultReason implements ResultReason {

    TOO_MANY_STUDENTS("Too many students to close the course."),
    INCORRECT_COURSE_ID("Incorrect course id");

    String text;

    @Override
    public String text() {
        return text;
    }
}
