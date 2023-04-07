package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;

interface CourseCreator {

    CourseCreationResult considerCourseCreation(CourseProposition courseProposition);
}
