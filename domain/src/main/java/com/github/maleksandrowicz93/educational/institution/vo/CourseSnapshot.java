package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import lombok.Builder;

import java.util.Set;

@Builder
public record CourseSnapshot(
        CourseId id,
        String name,
        FacultyId facultyId,
        ProfessorId professorId,
        Set<FieldOfStudyId> fieldsOfStudy,
        Set<StudentId> students,
        CourseState state
) {
}
