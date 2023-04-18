package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Application;
import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

@Builder
public record ProfessorApplication(
        @NonNull PersonalData personalData,
        @NonNull YearsOfExperience yearsOfExperience,
        @NonNull Set<FieldOfStudyId> fieldsOfStudy
) implements Application {
}
