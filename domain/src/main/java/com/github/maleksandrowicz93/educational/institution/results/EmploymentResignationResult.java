package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class EmploymentResignationResult extends Result<ProfessorSnapshot, EmploymentResignationResultReason> {

    public EmploymentResignationResult(ProfessorSnapshot value) {
        super(value);
    }

    public EmploymentResignationResult(EmploymentResignationResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public EmploymentResignationResult(
            EmploymentResignationResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected EmploymentResignationResultReason defaultResultReason() {
        return EmploymentResignationResultReason.SUCCESS;
    }
}
