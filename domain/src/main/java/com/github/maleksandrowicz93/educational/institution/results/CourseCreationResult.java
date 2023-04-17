package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;

public class CourseCreationResult extends Result<CourseSnapshot> {

    public CourseCreationResult(CourseSnapshot value) {
        super(value);
    }
}
