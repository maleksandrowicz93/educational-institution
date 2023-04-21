package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.domain.service.CourseManagementService;
import com.github.maleksandrowicz93.educational.institution.api.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DomainCourseManagementService implements CourseManagementService {

    CourseRepository courseRepository;
    EventsPublisher eventsPublisher;

    @Override
    public Result<CourseSnapshot> resignFromLeadingCourse(CourseId courseId) {
        return null;
    }

    @Override
    public Result<CourseSnapshot> closeCourse(CourseId courseId) {
        return null;
    }
}
