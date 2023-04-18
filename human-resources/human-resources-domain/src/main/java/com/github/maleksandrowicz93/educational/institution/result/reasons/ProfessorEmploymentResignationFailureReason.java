package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum ProfessorEmploymentResignationFailureReason implements ResultReason {

    PROFESSOR_NOT_EMPLOYED("Professor not employed at the faculty.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
