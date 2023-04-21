package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.api.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import lombok.NonNull;

public record FacultyCreatedEvent(
        @NonNull RectorOfficeId rectorOfficeId,
        @NonNull EducationalInstitutionThresholds educationalInstitutionThresholds,
        @NonNull FacultySnapshot facultySnapshot

) implements DomainEvent {
}
