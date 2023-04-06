package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class Student implements Entity<StudentSnapshot, StudentId> {

    StudentId id;
    PersonalData personalData;
    FacultyId facultyId;
    Set<CourseId> courses;
    EnrollmentState enrollmentState;

    static Student from(StudentSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .personalData(snapshot.personalData())
                .facultyId(snapshot.facultyId())
                .courses(snapshot.courses())
                .enrollmentState(snapshot.enrollmentState())
                .build();
    }

    @Override
    public StudentSnapshot createSnapshot() {
        return StudentSnapshot.builder()
                .id(id)
                .personalData(personalData)
                .facultyId(facultyId)
                .courses(courses)
                .enrollmentState(enrollmentState)
                .build();
    }
}
