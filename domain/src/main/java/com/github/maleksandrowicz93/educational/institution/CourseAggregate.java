package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

interface CourseAggregate extends Aggregate<CourseSnapshot> {

    CourseEnrollmentResult considerCourseEnrollment(StudentSnapshot studentSnapshot);

    CourseClosingResult considerClosingCourse();

    CourseSnapshot receiveCourseLeadingResignation();

    CourseOvertakingResult considerCourseOvertaking(ProfessorSnapshot professor);
}
