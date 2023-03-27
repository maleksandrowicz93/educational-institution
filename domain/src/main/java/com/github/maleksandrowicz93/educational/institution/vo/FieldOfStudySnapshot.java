package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;

@Builder
public record FieldOfStudySnapshot(
        FieldOfStudyId id,
        String name,
        FacultyId facultyId
) {
}
