package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class FacultyCreationResult extends Result<FacultySnapshot, FacultyCreationResultReason> {

    public FacultyCreationResult(FacultySnapshot value) {
        super(value);
    }

    public FacultyCreationResult(FacultyCreationResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public FacultyCreationResult(
            FacultyCreationResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected FacultyCreationResultReason defaultResultReason() {
        return FacultyCreationResultReason.SUCCESS;
    }
}
