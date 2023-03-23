package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class Faculty implements Entity<FacultySnapshot> {

    FacultyId id;
    String name;
    FieldOfStudy mainFieldOfStudy;
    Set<FieldOfStudy> secondaryFieldsOfStudy;
    Set<Professor> professors;
    Set<Course> courses;
    Set<Student> students;

    static Faculty from(FacultySnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .mainFieldOfStudy(FieldOfStudy.from(snapshot.mainFieldOfStudy()))
                .secondaryFieldsOfStudy(snapshot.secondaryFieldsOfStudy().stream()
                        .map(FieldOfStudy::from)
                        .collect(toSet()))
                .professors(snapshot.professors().stream()
                        .map(Professor::from)
                        .collect(toSet()))
                .courses(snapshot.courses().stream()
                        .map(Course::from)
                        .collect(toSet()))
                .students(snapshot.students().stream()
                        .map(Student::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return FacultySnapshot.builder()
                .id(id)
                .name(name)
                .mainFieldOfStudy(mainFieldOfStudy.createSnapshot())
                .secondaryFieldsOfStudy(secondaryFieldsOfStudy.stream()
                        .map(FieldOfStudy::createSnapshot)
                        .collect(toSet()))
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .courses(courses.stream()
                        .map(Course::createSnapshot)
                        .collect(toSet()))
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(toSet()))
                .build();
    }
}
