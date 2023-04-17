package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.CourseCreatorAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@SuperBuilder
class CourseCreatorAggregateRoot implements CourseCreatorAggregate {

    @Getter(PRIVATE)
    final FacultySnapshot source;
    @Getter
    final FacultyId id;
    final CourseCreationThresholds thresholds;

    Set<Professor> professors;
    Set<CourseId> courses;

    static CourseCreatorAggregate from(FacultySnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .thresholds(source.facultyManagementThresholds().courseCreationThresholds())
                .professors(source.professors().stream()
                        .map(Professor::from)
                        .collect(toSet()))
                .courses(source.courses())
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return source().toBuilder()
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .courses(courses)
                .build();
    }

    @Override
    public Result<CourseSnapshot> considerCourseCreation(CourseProposition courseProposition) {
        return null;
    }
}
