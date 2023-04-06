package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;

interface CourseCloser extends CourseAggregate {

    CourseClosingResult considerClosingCourse();
}
