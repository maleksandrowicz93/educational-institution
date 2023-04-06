package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record StudentSnapshot(
        StudentId id,
        PersonalData personalData,
        FacultyId facultyId,
        @Singular Set<CourseId> courses,
        EnrollmentState enrollmentState
) implements Snapshot<StudentId> {
}
