package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.common.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import lombok.Singular;

import java.util.Set;

public record FacultyCreatedEvent(
        FacultyId facultyId,
        String name,
        RectorOfficeId rectorOfficeId,
        FacultyManagementThresholds facultyManagementThresholds,
        FieldOfStudySnapshot mainFieldOfStudy,
        @Singular("fieldOfStudy") Set<FieldOfStudySnapshot> secondaryFieldsOfStudy

) implements DomainEvent {
}
