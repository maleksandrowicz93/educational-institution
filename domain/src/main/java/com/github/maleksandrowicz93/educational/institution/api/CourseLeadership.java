package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;

public interface CourseLeadership {

    CourseSnapshot receiveCourseLeadingResignation();

    CourseOvertakingResult considerCourseOvertaking(ProfessorSnapshot professor);
}
