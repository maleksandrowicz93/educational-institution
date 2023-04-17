package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public class EnrollmentResignationResult extends Result<StudentSnapshot> {

    public EnrollmentResignationResult(StudentSnapshot value) {
        super(value);
    }
}
