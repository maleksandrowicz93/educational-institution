package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PACKAGE)
record CourseSnapshot(
        CourseId id,
        Name name,
        FacultyId facultyId,
        ProfessorId professorId,
        Set<FieldOfStudyId> fieldsOfStudy,
        Set<StudentId> students,
        CourseState sate
) {
}
