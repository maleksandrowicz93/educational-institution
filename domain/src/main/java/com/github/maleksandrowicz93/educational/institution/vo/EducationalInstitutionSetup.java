package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Builder
public record EducationalInstitutionSetup(
        @NonNull String name,
        @NonNull FacultyManagementThresholds thresholds
) {
    public EducationalInstitutionSetup {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Educational Institution name cannot be blank.");
        }
    }
}
