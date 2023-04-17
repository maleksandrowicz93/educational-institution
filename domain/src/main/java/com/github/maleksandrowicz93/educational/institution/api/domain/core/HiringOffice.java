package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;

public interface HiringOffice {

    Result<ProfessorSnapshot> considerHiring(ProfessorApplication professorApplication);

    Result<ProfessorSnapshot> receiveHiringResignation(ProfessorId professorId);
}
