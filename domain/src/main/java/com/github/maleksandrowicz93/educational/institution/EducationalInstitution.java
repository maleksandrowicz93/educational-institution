package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.Builder;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class EducationalInstitution implements EducationalInstitutionAggregate {

    EducationalInstitutionId id;
    String name;
    Set<FacultyId> faculties;

    static EducationalInstitution from(EducationalInstitutionSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .faculties(snapshot.faculties())
                .build();
    }

    @Override
    public EducationalInstitutionSnapshot createSnapshot() {
        return EducationalInstitutionSnapshot.builder()
                .id(id)
                .name(name)
                .faculties(faculties)
                .build();
    }

    @Override
    public FacultySnapshot createFaculty(FacultySetup facultySetup) {
        return null;
    }
}
