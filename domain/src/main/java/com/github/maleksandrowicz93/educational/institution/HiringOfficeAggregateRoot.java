package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.HiringOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@SuperBuilder
class HiringOfficeAggregateRoot extends HiringOfficeAggregate {

    final FacultyId facultyId;
    final ProfessorHiringThresholds thresholds;

    Set<Professor> professors;

    static HiringOfficeAggregate from(FacultySnapshot source) {
        return builder()
                .source(source)
                .facultyId(source.id())
                .thresholds(source.facultyManagementThresholds().professorHiringThresholds())
                .professors(source.professors().stream()
                        .map(Professor::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return source().toBuilder()
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public Result<ProfessorSnapshot> considerHiring(ProfessorApplication professorApplication) {
        return null;
    }

    @Override
    public Result<ProfessorSnapshot> receiveHiringResignation(ProfessorId professorId) {
        return null;
    }
}
