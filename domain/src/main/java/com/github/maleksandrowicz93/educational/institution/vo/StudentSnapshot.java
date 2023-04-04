package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
public record StudentSnapshot(
        StudentId id,
        PersonalData personalData,
        FacultyId facultyId,
        Set<CourseId> courses,
        EnrollmentState enrollmentState
) {
}
