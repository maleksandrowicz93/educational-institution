package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class Student implements Entity<StudentSnapshot, StudentId> {

    @Getter
    StudentId id;
    PersonalData personalData;
    EnrollmentState enrollmentState;

    static Student from(StudentSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .personalData(snapshot.personalData())
                .enrollmentState(snapshot.enrollmentState())
                .build();
    }

    @Override
    public StudentSnapshot createSnapshot() {
        return StudentSnapshot.builder()
                .id(id)
                .personalData(personalData)
                .enrollmentState(enrollmentState)
                .build();
    }
}
