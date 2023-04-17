package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;

public interface CourseCloser {

    Result<CourseSnapshot> considerClosingCourse();
}
