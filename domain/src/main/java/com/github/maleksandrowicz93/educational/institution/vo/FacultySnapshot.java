package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public record FacultySnapshot(
        FacultyId id,
        EducationalInstitutionId educationalInstitutionId,
        String name,
        FacultyManagementThresholds facultyManagementThresholds,
        FieldOfStudySnapshot mainFieldOfStudy,
        Set<FieldOfStudySnapshot> secondaryFieldsOfStudy,
        Set<ProfessorSnapshot> professors,
        Set<StudentSnapshot> students,
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