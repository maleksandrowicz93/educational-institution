package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Application;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

@Builder
public record StudentApplication(
        @NonNull PersonalData personalData,
        @NonNull TestResult mainTestResult,
        @Singular(value = "secondaryTestResult", ignoreNullCollections = true) Set<TestResult> secondaryTestsResults
) implements Application {
}
