package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.CourseCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;

public interface DeanOffice {

    Result<CourseCreatedEvent> considerCourseCreation(CourseProposition courseProposition);
}
