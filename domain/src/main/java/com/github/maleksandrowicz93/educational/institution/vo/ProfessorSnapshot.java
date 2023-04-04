package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
public record ProfessorSnapshot(
        ProfessorId id,
        PersonalData personalData,
        FacultyId facultyId,
        Set<FieldOfStudySnapshot> fieldsOfStudy,
        Set<CourseId> ledCourses,
        EmploymentState employmentState
) {
    public ProfessorSnapshot {
        if (fieldsOfStudy == null) {
            fieldsOfStudy = new HashSet<>();
        }
        if (ledCourses == null) {
            ledCourses = new HashSet<>();
        }
    }
}
