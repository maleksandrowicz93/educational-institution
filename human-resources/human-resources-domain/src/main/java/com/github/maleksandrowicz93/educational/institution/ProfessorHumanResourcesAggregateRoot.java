package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.ProfessorHumanResourcesAggregate;
import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.events.ProfessorEmployedEvent;
import com.github.maleksandrowicz93.educational.institution.events.ProfessorResignedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHumanResourcesSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@FieldDefaults(makeFinal = true)
class ProfessorHumanResourcesAggregateRoot implements ProfessorHumanResourcesAggregate {

    @Getter
    FacultyId id;
    ProfessorHiringThresholds thresholds;
    FieldOfStudyId mainFieldOfStudy;
    Set<FieldOfStudyId> secondaryFieldsOfStudy;
    Set<Professor> professors;

    static ProfessorHumanResourcesAggregateRoot from(ProfessorHumanResourcesSnapshot source) {
        return builder()
                .id(source.id())
                .thresholds(source.thresholds())
                .mainFieldOfStudy(source.mainFieldOfStudy())
                .secondaryFieldsOfStudy(source.secondaryFieldsOfStudy())
                .professors(source.professors().stream()
                        .map(Professor::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public ProfessorHumanResourcesSnapshot createSnapshot() {
        return ProfessorHumanResourcesSnapshot.builder()
                .id(id)
                .thresholds(thresholds)
                .mainFieldOfStudy(mainFieldOfStudy)
                .secondaryFieldsOfStudy(secondaryFieldsOfStudy)
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public Result<ProfessorEmployedEvent> considerApplication(ProfessorApplication professorApplication) {
        return null;
    }

    @Override
    public Result<ProfessorResignedEvent> receiveResignation(ProfessorId professorId) {
        return null;
    }
}
