package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PRIVATE)
class Professor implements Entity<ProfessorSnapshot> {

    ProfessorId id;
    PersonalData personalData;
    Set<FieldOfStudyId> fieldsOfStudy;
    FacultyId facultyId;
    EmploymentState employmentState;

    static Professor from(ProfessorSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .personalData(snapshot.personalData())
                .fieldsOfStudy(snapshot.fieldsOfStudy())
                .facultyId(snapshot.facultyId())
                .employmentState(snapshot.employmentState())
                .build();
    }

    @Override
    public ProfessorSnapshot createSnapshot() {
        return ProfessorSnapshot.builder()
                .id(id)
                .personalData(personalData)
                .fieldsOfStudy(fieldsOfStudy)
                .facultyId(facultyId)
                .employmentState(employmentState)
                .build();
    }
}
