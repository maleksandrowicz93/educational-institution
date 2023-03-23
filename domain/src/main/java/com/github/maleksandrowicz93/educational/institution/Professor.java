package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import lombok.Builder;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
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
