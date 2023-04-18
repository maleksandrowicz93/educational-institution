package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.DeanOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.CourseCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.Course;
import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.DeanOfficeSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.Professor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class DeanOfficeAggregateRoot implements DeanOfficeAggregate {

    final DeanOfficeSnapshot source;
    @Getter
    final FacultyId id;
    final CourseCreationThresholds thresholds;
    final Set<Professor> professors;
    final Set<Course> courses;

    static DeanOfficeAggregateRoot from(DeanOfficeSnapshot source) {
        return null;
    }

    @Override
    public DeanOfficeSnapshot createSnapshot() {
        return null;
    }

    @Override
    public Result<CourseCreatedEvent> considerCourseCreation(CourseProposition courseProposition) {
        return null;
    }
}
