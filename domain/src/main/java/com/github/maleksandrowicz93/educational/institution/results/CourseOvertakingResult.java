package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class CourseOvertakingResult extends Result<CourseSnapshot, CourseOvertakingResultReason> {

    public CourseOvertakingResult(CourseSnapshot value) {
        super(value);
    }

    public CourseOvertakingResult(CourseOvertakingResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public CourseOvertakingResult(
            CourseOvertakingResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected CourseOvertakingResultReason defaultResultReason() {
        return CourseOvertakingResultReason.SUCCESS;
    }
}
