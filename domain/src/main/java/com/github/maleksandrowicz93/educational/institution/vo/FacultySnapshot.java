package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;

import java.util.Set;

@Builder
public record FacultySnapshot(
        FacultyId id,
        String name,
        FieldOfStudySnapshot mainFieldOfStudy,
        Set<FieldOfStudySnapshot> secondaryFieldsOfStudy,
        Set<ProfessorSnapshot> professors,
        Set<CourseSnapshot> courses,
        Set<StudentSnapshot> students
) {
}