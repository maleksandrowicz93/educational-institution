package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.Builder;

import java.util.Set;

import static lombok.AccessLevel.PACKAGE;

@Builder(access = PACKAGE)
class Course implements Entity<CourseSnapshot> {

    CourseId id;
    String name;
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
                .state(snapshot.state())
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
                .state(state)
                .build();
    }
}
