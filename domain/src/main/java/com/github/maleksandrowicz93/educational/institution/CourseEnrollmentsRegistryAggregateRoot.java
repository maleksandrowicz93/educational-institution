package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.CourseEnrollmentsRegistryAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@SuperBuilder
class CourseEnrollmentsRegistryAggregateRoot implements CourseEnrollmentsRegistryAggregate {

    @Getter(PRIVATE)
    final CourseSnapshot source;
    @Getter
    final CourseId id;
    final Threshold maximumNumberOfEnrolledStudents;

    Set<Student> students;

    static CourseEnrollmentsRegistryAggregate from(CourseSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
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
    public Result<StudentSnapshot> considerCourseEnrollment(StudentSnapshot student) {
        return null;
    }
}
