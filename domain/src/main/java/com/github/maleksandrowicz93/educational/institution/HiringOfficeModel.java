package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.HiringResult;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class HiringOfficeModel implements HiringOffice {

    final FacultySnapshot source;
    final FacultyId facultyId;
    final ProfessorHiringThresholds thresholds;

    Set<Professor> professors;

    static HiringOffice from(FacultySnapshot source) {
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
        return source.toBuilder()
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public HiringResult considerHiring(ProfessorApplication professorApplication) {
        return null;
    }

    @Override
    public EmploymentResignationResult receiveHiringResignation(ProfessorId professorId) {
        return null;
    }
}
