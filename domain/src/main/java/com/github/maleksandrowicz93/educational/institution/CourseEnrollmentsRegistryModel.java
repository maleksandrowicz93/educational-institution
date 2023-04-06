package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseEnrollmentsRegistryModel implements CourseEnrollmentsRegistry {

    final CourseSnapshot source;
    final CourseId courseId;
    final Threshold maximumNumberOfEnrolledStudents;

    Set<Student> students;

    static CourseEnrollmentsRegistry from(CourseSnapshot source) {
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
        return source.toBuilder()
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
