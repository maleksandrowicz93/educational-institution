package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum CourseCreationResultReason implements ResultReason {

    SUCCESS("Success."),
    TOO_FEW_FIELDS_OF_STUDY("Too few fields of study."),
    TOO_MANY_FIELDS_OF_STUDY("Too many fields of study"),
    PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED("Professor's fields of study not matched."),
    NO_PROFESSOR_CAPACITY("Professor does not have capacity for leading the course."),
    NO_CAPACITY_AT_FACULTY("Faculty is full of courses."),
    INCORRECT_FACULTY_ID("Incorrect faculty id."),
    INCORRECT_PROFESSOR_ID("Incorrect professor id.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
