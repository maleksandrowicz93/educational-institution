package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseEntity implements Entity<CourseSnapshot, CourseId> {

    @Getter
    CourseId id;
    String name;
    ProfessorId professorId;
    Set<FieldOfStudyId> fieldsOfStudy;

    static CourseEntity from(CourseSnapshot source) {
        return builder()
                .id(source.id())
                .name(source.name())
                .professorId(source.professorId())
                .fieldsOfStudy(source.fieldsOfStudy())
                .build();
    }

    @Override
    public CourseSnapshot createSnapshot() {
        return CourseSnapshot.builder()
                .id(id)
                .name(name)
                .professorId(professorId)
                .fieldsOfStudy(fieldsOfStudy)
                .build();
    }
}
