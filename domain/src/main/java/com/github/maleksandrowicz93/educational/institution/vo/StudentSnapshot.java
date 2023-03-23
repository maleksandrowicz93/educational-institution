package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import lombok.Builder;

@Builder
public record StudentSnapshot(
        StudentId id,
        PersonalData personalData,
        FacultyId facultyId,
        EnrollmentState enrollmentState
) {
}
