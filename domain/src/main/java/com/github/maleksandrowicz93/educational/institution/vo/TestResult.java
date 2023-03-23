package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.NonNull;

public record TestResult(
        int percentage,
        @NonNull FieldOfStudyId fieldOfStudyId
) {
    public TestResult {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException();
        }
    }
}
