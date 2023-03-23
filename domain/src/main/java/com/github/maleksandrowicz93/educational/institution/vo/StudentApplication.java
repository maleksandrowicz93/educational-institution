package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public record StudentApplication(
        @NonNull PersonalData personalData,
        @NonNull TestResult mainTestResult,
        @NonNull List<TestResult> secondaryTestsResults,
        @NonNull FacultyId facultyId
) {
}
