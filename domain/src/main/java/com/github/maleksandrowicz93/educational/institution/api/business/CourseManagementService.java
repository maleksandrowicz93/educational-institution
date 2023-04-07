package com.github.maleksandrowicz93.educational.institution.api.business;

import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseLeadingResignationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;

public interface CourseManagementService {

    CourseLeadingResignationResult resignFromLeadingCourse(CourseId courseId);

    CourseClosingResult closeCourse(CourseId courseId);
}
