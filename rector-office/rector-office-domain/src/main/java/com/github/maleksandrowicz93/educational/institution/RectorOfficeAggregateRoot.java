package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.FacultyCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@FieldDefaults(makeFinal = true)
class RectorOfficeAggregateRoot implements RectorOfficeAggregate {

    @Getter
    final RectorOfficeId id;
    final String name;
    final EducationalInstitutionThresholds thresholds;
    final Set<FacultyEntity> faculties;

    static RectorOfficeAggregate from(RectorOfficeSnapshot source) {
        return builder()
                .id(source.id())
                .name(source.name())
                .thresholds(source.thresholds())
                .faculties(source.faculties().stream()
                        .map(FacultyEntity::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public RectorOfficeSnapshot createSnapshot() {
        return RectorOfficeSnapshot.builder()
                .id(id)
                .name(name)
                .thresholds(thresholds)
                .faculties(faculties.stream()
                        .map(FacultyEntity::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public Result<FacultyCreatedEvent> createFaculty(FacultySetup facultySetup) {
        return null;
    }
}
