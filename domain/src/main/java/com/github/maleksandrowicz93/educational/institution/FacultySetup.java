package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PACKAGE)
record FacultySetup(
        Name facultyName,
        Set<FieldOfStudySnapshot> fieldsOfStudy
) {
}
