package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.CourseBecomeFreeEvent;
import com.github.maleksandrowicz93.educational.institution.events.CourseOvertakenEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication;

public interface CourseLeadership {

    Result<CourseBecomeFreeEvent> receiveCourseLeadingResignation();

    Result<CourseOvertakenEvent> considerCourseOvertaking(CourseOvertakingApplication application);
}
