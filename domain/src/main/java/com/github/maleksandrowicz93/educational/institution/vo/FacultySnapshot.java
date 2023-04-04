package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
public record FacultySnapshot(
        FacultyId id,
        EducationalInstitutionId educationalInstitutionId,
        String name,
        FacultyManagementThresholds facultyManagementThresholds,
        FieldOfStudySnapshot mainFieldOfStudy,
        Set<FieldOfStudySnapshot> secondaryFieldsOfStudy,
        @Singular Set<ProfessorSnapshot> professors,
        @Singular Set<StudentSnapshot> students,
        Set<CourseId> courses
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