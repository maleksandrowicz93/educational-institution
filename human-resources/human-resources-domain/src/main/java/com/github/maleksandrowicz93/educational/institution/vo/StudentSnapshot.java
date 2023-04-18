package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import lombok.Builder;

@Builder(toBuilder = true)
public record StudentSnapshot(
        StudentId id,
        PersonalData personalData,
        EnrollmentState enrollmentState
) implements Snapshot<StudentId> {
}
