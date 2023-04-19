package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.common.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import lombok.NonNull;

public record FacultyCreatedEvent(
        @NonNull RectorOfficeId rectorOfficeId,
        @NonNull FacultyManagementThresholds facultyManagementThresholds,
        @NonNull FacultySnapshot facultySnapshot

) implements DomainEvent {
}
