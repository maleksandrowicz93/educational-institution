package com.github.maleksandrowicz93.educational.institution.api.domain.service;

import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;

public interface CourseCreationService {

    CourseCreationResult createCourse(CourseProposition courseProposition);
}
