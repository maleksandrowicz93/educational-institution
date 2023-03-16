package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
class Faculty implements Entity<FacultySnapshot> {

    FacultyId id;
    Name name;
    Set<FieldOfStudy> fieldsOfStudy;
    Set<Professor> professors;
    Set<Course> courses;
    Set<Student> students;

    static Faculty from(FacultySnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .fieldsOfStudy(snapshot.fieldsOfStudy().stream()
                        .map(FieldOfStudy::from)
                        .collect(Collectors.toSet()))
                .professors(snapshot.professors().stream()
                        .map(Professor::from)
                        .collect(Collectors.toSet()))
                .courses(snapshot.courses().stream()
                        .map(Course::from)
                        .collect(Collectors.toSet()))
                .students(snapshot.students().stream()
                        .map(Student::from)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return FacultySnapshot.builder()
                .id(id)
                .name(name)
                .fieldsOfStudy(fieldsOfStudy.stream()
                        .map(FieldOfStudy::createSnapshot)
                        .collect(Collectors.toSet()))
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(Collectors.toSet()))
                .courses(courses.stream()
                        .map(Course::createSnapshot)
                        .collect(Collectors.toSet()))
                .students(students.stream()
                        .map(Student::createSnapshot)
                        .collect(Collectors.toSet()))
                .build();
    }
}
