package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@SuperBuilder
class CourseEnrollmentsRegistryModel extends CourseEnrollmentsRegistryAggregate {

    final CourseId courseId;
    final Threshold maximumNumberOfEnrolledStudents;

    Set<Student> students;

    static CourseEnrollmentsRegistryAggregate from(CourseSnapshot source) {
        return builder()
                .source(source)
                .courseId(source.id())
                .maximumNumberOfEnrolledStudents(source.courseManagementThresholds().maximumNumberOfEnrolledStudents())
                .students(source.students().stream()
                        .map(Student::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public CourseSnapshot createSnapshot() {
        return source().toBuilder()
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public CourseEnrollmentResult considerCourseEnrollment(StudentSnapshot student) {
        return null;
    }
}
