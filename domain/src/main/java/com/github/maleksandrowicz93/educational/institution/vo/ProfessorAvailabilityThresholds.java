package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder(toBuilder = true)
public record ProfessorAvailabilityThresholds(@NonNull Threshold maximumLedCourses) {
}
