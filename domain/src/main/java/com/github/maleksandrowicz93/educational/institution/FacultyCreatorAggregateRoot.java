package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.FacultyCreatorAggregate;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
class FacultyCreatorAggregateRoot extends FacultyCreatorAggregate {

    final EducationalInstitutionId id;
    Set<FacultyId> faculties;

    static FacultyCreatorAggregate from(EducationalInstitutionSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .faculties(source.faculties())
                .build();
    }

    @Override
    public EducationalInstitutionSnapshot createSnapshot() {
        return source().toBuilder()
                .faculties(faculties)
                .build();
    }

    @Override
    public FacultySnapshot createFaculty(FacultySetup facultySetup) {
        return null;
    }
}
