package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;

public interface CourseLeadership {

    CourseSnapshot receiveCourseLeadingResignation();

    Result<CourseSnapshot> considerCourseOvertaking(ProfessorSnapshot professor);
}
