package com.github.maleksandrowicz93.educational.institution.api.domain.service;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;

public interface CourseCreationService {

    Result<CourseSnapshot> createCourse(CourseProposition courseProposition);
}
