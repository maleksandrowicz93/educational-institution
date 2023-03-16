package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PRIVATE)
class Course implements Entity<CourseSnapshot> {

    CourseId id;
    Name name;
    FacultyId facultyId;
    ProfessorId professorId;
    Set<FieldOfStudyId> fieldsOfStudy;
    Set<StudentId> students;
    CourseState state;

    static Course from(CourseSnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .facultyId(snapshot.facultyId())
                .professorId(snapshot.professorId())
                .fieldsOfStudy(snapshot.fieldsOfStudy())
                .students(snapshot.students())
                .state(snapshot.sate())
                .build();
    }

    @Override
    public CourseSnapshot createSnapshot() {
        return CourseSnapshot.builder()
                .id(id)
                .name(name)
                .facultyId(facultyId)
                .professorId(professorId)
                .fieldsOfStudy(fieldsOfStudy)
                .students(students)
                .sate(state)
                .build();
    }
}
