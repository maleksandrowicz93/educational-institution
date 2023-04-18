package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Application;
import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

@Builder
public record StudentApplication(
        @NonNull PersonalData personalData,
        @NonNull TestResult mainTestResult,
        @NonNull Set<TestResult> secondaryTestsResults
) implements Application {
}
