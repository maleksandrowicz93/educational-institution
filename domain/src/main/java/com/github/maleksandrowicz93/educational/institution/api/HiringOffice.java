package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.HiringResult;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;

public interface HiringOffice {

    HiringResult considerHiring(ProfessorApplication professorApplication);

    EmploymentResignationResult receiveHiringResignation(ProfessorId professorId);
}
