package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public class CourseEnrollmentResult extends Result<StudentSnapshot> {

    public CourseEnrollmentResult(StudentSnapshot value) {
        super(value);
    }
}
