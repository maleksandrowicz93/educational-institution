package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

@Builder
public record StudentApplication(
        @NonNull PersonalData personalData,
        @NonNull TestResult mainTestResult,
        @NonNull Set<TestResult> secondaryTestsResults,
        @NonNull FacultyId facultyId
) {
}
