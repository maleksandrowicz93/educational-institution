package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class Professor implements Entity<ProfessorSnapshot, ProfessorId> {

    ProfessorId id;
    PersonalData personalData;
    FacultyId facultyId;
    Set<FieldOfStudy> fieldsOfStudy;
    Set<CourseId> ledCourses;
    EmploymentState employmentState;

    static Professor from(ProfessorSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .personalData(snapshot.personalData())
                .facultyId(snapshot.facultyId())
                .fieldsOfStudy(snapshot.fieldsOfStudy().stream()
                        .map(FieldOfStudy::from)
                        .collect(toSet()))
                .ledCourses(snapshot.ledCourses())
                .employmentState(snapshot.employmentState())
                .build();
    }

    @Override
    public ProfessorSnapshot createSnapshot() {
        return ProfessorSnapshot.builder()
                .id(id)
                .personalData(personalData)
                .facultyId(facultyId)
                .fieldsOfStudy(fieldsOfStudy.stream()
                        .map(FieldOfStudy::createSnapshot)
                        .collect(toSet()))
                .ledCourses(ledCourses)
                .employmentState(employmentState)
                .build();
    }
}
