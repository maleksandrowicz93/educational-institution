package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;

import java.util.HashSet;
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
    public FacultySnapshot {
        if (secondaryFieldsOfStudy == null) {
            secondaryFieldsOfStudy = new HashSet<>();
        }
        if (professors == null) {
            professors = new HashSet<>();
        }
        if (courses == null) {
            courses = new HashSet<>();
        }
        if (students == null) {
            students = new HashSet<>();
        }
    }
}