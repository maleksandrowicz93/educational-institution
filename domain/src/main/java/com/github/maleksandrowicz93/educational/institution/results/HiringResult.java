package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class HiringResult extends Result<ProfessorSnapshot, HiringResultReason> {

    public HiringResult(ProfessorSnapshot value) {
        super(value);
    }

    public HiringResult(HiringResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public HiringResult(
            HiringResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected HiringResultReason defaultResultReason() {
        return HiringResultReason.SUCCESS;
    }
}
