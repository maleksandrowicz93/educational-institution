package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.business.CourseManagementService;
import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository;
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseLeadingResignationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DomainCourseManagementService implements CourseManagementService {

    CourseRepository courseRepository;
    EventsPublisher eventsPublisher;

    @Override
    public CourseLeadingResignationResult resignFromLeadingCourse(CourseId courseId) {
        return null;
    }

    @Override
    public CourseClosingResult closeCourse(CourseId courseId) {
        return null;
    }
}
