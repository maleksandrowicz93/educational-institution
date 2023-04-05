package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class EnrollmentResignationResult extends Result<StudentSnapshot, EnrollmentResignationResultReason> {

    public EnrollmentResignationResult(StudentSnapshot value) {
        super(value);
    }

    public EnrollmentResignationResult(EnrollmentResignationResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public EnrollmentResignationResult(
            EnrollmentResignationResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected EnrollmentResignationResultReason defaultResultReason() {
        return EnrollmentResignationResultReason.SUCCESS;
    }
}
