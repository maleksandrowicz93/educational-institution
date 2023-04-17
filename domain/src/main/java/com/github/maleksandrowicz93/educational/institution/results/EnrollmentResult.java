package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public class EnrollmentResult extends Result<StudentSnapshot> {

    public EnrollmentResult(StudentSnapshot value) {
        super(value);
    }
}
