package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class CourseClosingResult extends Result<CourseSnapshot, CourseClosingResultReason> {

    public CourseClosingResult(CourseSnapshot value) {
        super(value);
    }

    public CourseClosingResult(CourseClosingResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public CourseClosingResult(
            CourseClosingResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected CourseClosingResultReason defaultResultReason() {
        return CourseClosingResultReason.SUCCESS;
    }
}
