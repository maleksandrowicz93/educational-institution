package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class CourseLeadingResignationResult extends Result<CourseSnapshot, CourseLeadingResignationResultReason> {

    public CourseLeadingResignationResult(CourseSnapshot value) {
        super(value);
    }

    public CourseLeadingResignationResult(CourseLeadingResignationResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public CourseLeadingResignationResult(
            CourseLeadingResignationResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected CourseLeadingResignationResultReason defaultResultReason() {
        return CourseLeadingResignationResultReason.SUCCESS;
    }
}
