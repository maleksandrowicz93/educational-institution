package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PACKAGE)
record FacultySnapshot(
        FacultyId id,
        Name name,
        Set<FieldOfStudySnapshot> fieldsOfStudy,
        Set<ProfessorSnapshot> professors,
        Set<CourseSnapshot> courses,
        Set<StudentSnapshot> students
) {
}