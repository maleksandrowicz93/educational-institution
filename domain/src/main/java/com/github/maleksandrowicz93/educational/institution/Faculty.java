package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class Faculty implements FacultyAggregate {

    FacultyId id;
    EducationalInstitutionId educationalInstitutionId;
    String name;
    FacultyManagementThresholds facultyManagementThresholds;
    FieldOfStudy mainFieldOfStudy;
    Set<FieldOfStudy> secondaryFieldsOfStudy;
    Set<Professor> professors;
    Set<Student> students;
    Set<CourseId> courses;

    static Faculty from(FacultySnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .educationalInstitutionId(snapshot.educationalInstitutionId())
                .name(snapshot.name())
                .facultyManagementThresholds(snapshot.facultyManagementThresholds())
                .mainFieldOfStudy(FieldOfStudy.from(snapshot.mainFieldOfStudy()))
                .secondaryFieldsOfStudy(snapshot.secondaryFieldsOfStudy().stream()
                        .map(FieldOfStudy::from)
                        .collect(toSet()))
                .professors(snapshot.professors().stream()
                        .map(Professor::from)
                        .collect(toSet()))
                .students(snapshot.students().stream()
                        .map(Student::from)
                        .collect(toSet()))
                .courses(snapshot.courses())
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return FacultySnapshot.builder()
                .id(id)
                .name(name)
                .facultyManagementThresholds(facultyManagementThresholds)
                .educationalInstitutionId(educationalInstitutionId)
                .mainFieldOfStudy(mainFieldOfStudy.createSnapshot())
                .secondaryFieldsOfStudy(secondaryFieldsOfStudy.stream()
                        .map(FieldOfStudy::createSnapshot)
                        .collect(toSet()))
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(toSet()))
                .courses(courses)
                .build();
    }
}
