package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public interface CourseEnrollmentsRegistry {

    CourseEnrollmentResult considerCourseEnrollment(StudentSnapshot studentSnapshot);

}
