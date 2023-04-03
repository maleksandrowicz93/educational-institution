package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class EnrollmentResult extends Result<StudentSnapshot, EnrollmentResultReason> {

    public EnrollmentResult(StudentSnapshot value) {
        super(value);
    }

    public EnrollmentResult(EnrollmentResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public EnrollmentResult(
            EnrollmentResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected EnrollmentResultReason defaultResultReason() {
        return EnrollmentResultReason.SUCCESS;
    }
}
