package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseManagementThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;

@Builder(access = PACKAGE)
class Course implements CourseAggregate {

    CourseId id;
    String name;
    FacultyId facultyId;
    CourseManagementThresholds courseManagementThresholds;
    Set<FieldOfStudy> fieldsOfStudy;
    Professor professor;
    CourseState state;
    Set<Student> students;

    static Course from(CourseSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .facultyId(snapshot.facultyId())
                .courseManagementThresholds(snapshot.courseManagementThresholds())
                .fieldsOfStudy(snapshot.fieldsOfStudy().stream()
                        .map(FieldOfStudy::from)
                        .collect(toSet()))
                .professor(Professor.from(snapshot.professor()))
                .state(snapshot.state())
                .students(snapshot.students().stream()
                        .map(Student::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public CourseSnapshot createSnapshot() {
        return CourseSnapshot.builder()
                .id(id)
                .name(name)
                .facultyId(facultyId)
                .courseManagementThresholds(courseManagementThresholds)
                .fieldsOfStudy(fieldsOfStudy.stream()
                        .map(FieldOfStudy::createSnapshot)
                        .collect(toSet()))
                .professor(professor.createSnapshot())
                .state(state)
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(toSet()))
                .build();
    }
}
