package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.ProfessorHumanResourcesAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.ProfessorEmployedEvent;
import com.github.maleksandrowicz93.educational.institution.events.ProfessorResignedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHumanResourcesSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class ProfessorHumanResourcesAggregateRoot implements ProfessorHumanResourcesAggregate {


    final ProfessorHumanResourcesSnapshot source;
    @Getter
    final FacultyId id;
    final ProfessorHiringThresholds thresholds;

    Set<Professor> professors;

    static ProfessorHumanResourcesAggregateRoot from(ProfessorHumanResourcesSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .thresholds(source.thresholds())
                .professors(source.professors().stream()
                        .map(Professor::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public ProfessorHumanResourcesSnapshot createSnapshot() {
        return source.toBuilder()
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public Result<ProfessorEmployedEvent> apply(ProfessorApplication professorApplication) {
        return null;
    }

    @Override
    public ProfessorResignedEvent resign(ProfessorId professorId) {
        return null;
    }
}
