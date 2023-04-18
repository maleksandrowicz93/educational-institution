package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.StudentHumanResourcesAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.StudentEnrolledEvent;
import com.github.maleksandrowicz93.educational.institution.events.StudentResignedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentEnrollmentThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.StudentHumanResourcesSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class StudentHumanResourcesAggregateRoot implements StudentHumanResourcesAggregate {

    final StudentHumanResourcesSnapshot source;
    @Getter
    final FacultyId id;
    final StudentEnrollmentThresholds thresholds;
    final FieldOfStudyId mainFieldOfStudy;
    final Set<FieldOfStudyId> secondaryFieldsOfStudy;

    Set<Student> students;

    static StudentHumanResourcesAggregateRoot from(StudentHumanResourcesSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .thresholds(source.thresholds())
                .mainFieldOfStudy(source.mainFieldOfStudy())
                .secondaryFieldsOfStudy(source.secondaryFieldsOfStudy())
                .students(source.students().stream()
                        .map(Student::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public StudentHumanResourcesSnapshot createSnapshot() {
        return source.toBuilder()
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public Result<StudentEnrolledEvent> considerApplication(StudentApplication studentApplication) {
        return null;
    }

    @Override
    public Result<StudentResignedEvent> receiveResignation(StudentId studentId) {
        return null;
    }
}
