package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.CourseBecameFreeEvent;
import com.github.maleksandrowicz93.educational.institution.events.CourseOvertakenEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication;

public interface CourseLeadership {

    Result<CourseBecameFreeEvent> receiveCourseLeadingResignation();

    Result<CourseOvertakenEvent> considerCourseOvertaking(CourseOvertakingApplication application);
}
