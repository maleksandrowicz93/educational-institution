package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

public class CourseCreationResult extends Result<CourseSnapshot, CourseCreationResultReason> {

    public CourseCreationResult(CourseSnapshot value) {
        super(value);
    }

    public CourseCreationResult(CourseCreationResultReason resultReason) {
        super(resultReason);
    }

    @Builder(builderMethodName = "failureWIthProperties", builderClassName = "FailureWIthPropertiesBuilder")
    public CourseCreationResult(
            CourseCreationResultReason resultReason,
            @Singular(ignoreNullCollections = true) Map<String, String> additionalProperties
    ) {
        super(resultReason, additionalProperties);
    }

    @Override
    protected CourseCreationResultReason defaultResultReason() {
        return CourseCreationResultReason.SUCCESS;
    }
}
