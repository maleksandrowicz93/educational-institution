package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseOvertakingFailureReason implements ResultReason {

    PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED("Professor's fields of study not matched."),
    NO_PROFESSOR_CAPACITY("Professor does not have capacity for overtaking the course."),
    LEAD_BY_OTHER_PROFESSOR("The course is already led by other professor.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
