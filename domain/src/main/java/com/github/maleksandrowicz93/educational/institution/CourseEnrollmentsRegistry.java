package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

interface CourseEnrollmentsRegistry {

    CourseEnrollmentResult considerCourseEnrollment(StudentSnapshot studentSnapshot);

}
