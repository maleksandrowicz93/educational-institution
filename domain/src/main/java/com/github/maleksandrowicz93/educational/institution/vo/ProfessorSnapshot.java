package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import lombok.Builder;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
public record ProfessorSnapshot(
        ProfessorId id,
        PersonalData personalData,
        FacultyId facultyId,
        @Singular("fieldOfStudy") Set<FieldOfStudySnapshot> fieldsOfStudy,
        @Singular Set<CourseId> ledCourses,
        EmploymentState employmentState
) implements Snapshot<ProfessorId> {
    public ProfessorSnapshot {
        if (fieldsOfStudy == null) {
            fieldsOfStudy = new HashSet<>();
        }
        if (ledCourses == null) {
            ledCourses = new HashSet<>();
        }
    }
}
