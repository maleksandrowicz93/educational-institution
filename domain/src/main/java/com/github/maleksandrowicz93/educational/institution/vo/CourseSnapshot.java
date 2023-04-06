package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import lombok.Builder;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
public record CourseSnapshot(
        CourseId id,
        String name,
        FacultyId facultyId,
        CourseManagementThresholds courseManagementThresholds,
        @Singular("fieldOfStudy") Set<FieldOfStudySnapshot> fieldsOfStudy,
        ProfessorSnapshot professor,
        CourseState state,
        @Singular Set<StudentSnapshot> students
) implements Snapshot<CourseId> {
    public CourseSnapshot {
        if (fieldsOfStudy == null) {
            fieldsOfStudy = new HashSet<>();
        }
        if (students == null) {
            students = new HashSet<>();
        }
    }
}
