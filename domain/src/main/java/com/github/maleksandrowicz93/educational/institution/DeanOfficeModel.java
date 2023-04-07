package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentEnrollmentThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@SuperBuilder
class DeanOfficeModel extends DeanOfficeAggregate {

    final FacultyId id;
    final StudentEnrollmentThresholds thresholds;

    Set<Student> students;

    static DeanOfficeAggregate from(FacultySnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .thresholds(source.facultyManagementThresholds().studentEnrollmentThresholds())
                .students(source.students().stream()
                        .map(Student::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return source().toBuilder()
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public EnrollmentResult considerEnrollment(StudentApplication studentApplication) {
        return null;
    }

    @Override
    public EnrollmentResignationResult receiveEnrollmentResignation(StudentId studentId) {
        return null;
    }
}
