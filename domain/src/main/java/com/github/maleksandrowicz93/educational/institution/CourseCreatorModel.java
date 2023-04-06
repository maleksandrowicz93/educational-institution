package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseCreatorModel implements CourseCreator {

    final FacultySnapshot source;
    final FacultyId id;
    final CourseCreationThresholds thresholds;

    Set<Professor> professors;
    Set<CourseId> courses;

    static CourseCreator from(FacultySnapshot source) {
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
        return source.toBuilder()
                .professors(professors.stream()
                        .map(Professor::createSnapshot)
                        .collect(toSet()))
                .courses(courses)
                .build();
    }

    @Override
    public CourseCreationResult considerCourseCreation(CourseProposition courseProposition) {
        return null;
    }
}
