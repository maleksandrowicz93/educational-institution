package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static lombok.AccessLevel.PACKAGE;

@Builder(access = PACKAGE)
class CourseEntity implements Entity<CourseSnapshot, CourseId> {

    @Getter
    final CourseId id;
    final String name;
    final ProfessorId leadingProfessor;
    final Set<FieldOfStudyId> fieldsOfStudy;

    static CourseEntity from(CourseSnapshot source) {
        return builder()
                .id(source.id())
                .name(source.name())
                .leadingProfessor(source.leadingProfessor())
                .fieldsOfStudy(source.fieldsOfStudy())
                .build();
    }

    @Override
    public CourseSnapshot createSnapshot() {
        return CourseSnapshot.builder()
                .id(id)
                .name(name)
                .leadingProfessor(leadingProfessor)
                .fieldsOfStudy(fieldsOfStudy)
                .build();
    }
}
