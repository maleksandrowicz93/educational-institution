package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;

public interface DeanOffice {

    EnrollmentResult considerEnrollment(StudentApplication studentApplication);

    EnrollmentResignationResult receiveEnrollmentResignation(StudentId studentId);
}
