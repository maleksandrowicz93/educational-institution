package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseOvertakingResultReason implements ResultReason {

    SUCCESS("Success."),
    PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED("Professor's fields of study not matched."),
    NO_PROFESSOR_CAPACITY("Professor does not have capacity for overtaking the course.");

    String text;

    @Override
    public String text() {
        return text;
    }
}