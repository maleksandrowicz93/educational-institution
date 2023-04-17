package com.github.maleksandrowicz93.educational.institution.api.domain.service;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;

public interface CourseManagementService {

    Result<CourseSnapshot> resignFromLeadingCourse(CourseId courseId);

    Result<CourseSnapshot> closeCourse(CourseId courseId);
}
