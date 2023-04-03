package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public record CourseSnapshot(
        CourseId id,
        String name,
        FacultyId facultyId,
        CourseManagementThresholds courseManagementThresholds,
        Set<FieldOfStudySnapshot> fieldsOfStudy,
        ProfessorSnapshot professor,
        CourseState state,
        Set<StudentSnapshot> students
) {
    public CourseSnapshot {
        if (fieldsOfStudy == null) {
            fieldsOfStudy = new HashSet<>();
        }
        if (students == null) {
            students = new HashSet<>();
        }
    }
}
