package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public interface DeanOffice {

    Result<StudentSnapshot> considerEnrollment(StudentApplication studentApplication);

    Result<StudentSnapshot> receiveEnrollmentResignation(StudentId studentId);
}
