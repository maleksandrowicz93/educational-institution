package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;

interface CourseLeadership {

    CourseSnapshot receiveCourseLeadingResignation();

    CourseOvertakingResult considerCourseOvertaking(ProfessorSnapshot professor);
}
