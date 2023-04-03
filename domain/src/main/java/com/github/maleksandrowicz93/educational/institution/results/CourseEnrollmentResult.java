package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class CourseEnrollmentResult extends Result<CourseSnapshot, CourseEnrollmentResultReason> {

    public CourseEnrollmentResult(CourseSnapshot value) {
        super(value);
    }

    public CourseEnrollmentResult(CourseEnrollmentResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public CourseEnrollmentResult(
            CourseEnrollmentResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected CourseEnrollmentResultReason defaultResultReason() {
        return CourseEnrollmentResultReason.SUCCESS;
    }
}
