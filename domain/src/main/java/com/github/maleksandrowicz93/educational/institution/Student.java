package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
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
