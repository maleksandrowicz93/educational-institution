package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.DeanOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.CourseCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.DeanOfficeSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.Professor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class DeanOfficeAggregateRoot implements DeanOfficeAggregate {

    final DeanOfficeSnapshot source;
    @Getter
    final FacultyId id;
    final CourseCreationThresholds thresholds;
    final Set<Professor> professors;
    final Set<CourseEntity> courses;

    static DeanOfficeAggregateRoot from(DeanOfficeSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .thresholds(source.thresholds())
                .professors(Set.copyOf(source.professors()))
                .courses(source.courses().stream()
                        .map(CourseEntity::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public DeanOfficeSnapshot createSnapshot() {
        return source.toBuilder()
                .courses(courses.stream()
                        .map(CourseEntity::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public Result<CourseCreatedEvent> considerCourseCreation(CourseProposition courseProposition) {
        return null;
    }
}
