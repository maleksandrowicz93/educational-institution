package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.common.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudy;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import lombok.NonNull;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public record FacultyCreatedEvent(
        @NonNull FacultyId facultyId,
        @NonNull String facultyName,
        @NonNull RectorOfficeId rectorOfficeId,
        @NonNull FacultyManagementThresholds facultyManagementThresholds,
        @NonNull FieldOfStudy mainFieldOfStudy,
        @Singular(value = "secondaryFieldOfStudy", ignoreNullCollections = true)
        Set<FieldOfStudy> secondaryFieldsOfStudy

) implements DomainEvent {
        public FacultyCreatedEvent {
                if (StringUtils.isBlank(facultyName)) {
                        throw new IllegalArgumentException("Faculty name cannot be blank");
                }
        }
}
