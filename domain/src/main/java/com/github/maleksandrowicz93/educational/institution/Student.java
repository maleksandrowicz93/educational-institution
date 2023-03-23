package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class Student implements Entity<StudentSnapshot> {

    StudentId id;
    PersonalData personalData;
    FacultyId facultyId;
    EnrollmentState enrollmentState;

    static Student from(StudentSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .personalData(snapshot.personalData())
                .facultyId(snapshot.facultyId())
                .enrollmentState(snapshot.enrollmentState())
                .build();
    }

    @Override
    public StudentSnapshot createSnapshot() {
        return StudentSnapshot.builder()
                .id(id)
                .personalData(personalData)
                .facultyId(facultyId)
                .enrollmentState(enrollmentState)
                .build();
    }
}
