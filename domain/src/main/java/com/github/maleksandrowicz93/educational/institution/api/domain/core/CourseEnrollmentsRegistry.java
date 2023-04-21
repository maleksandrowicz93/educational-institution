package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public interface CourseEnrollmentsRegistry {

    Result<StudentSnapshot> considerCourseEnrollment(StudentSnapshot studentSnapshot);

}
