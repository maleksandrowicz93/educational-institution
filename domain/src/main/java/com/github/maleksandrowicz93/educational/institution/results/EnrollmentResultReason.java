package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum EnrollmentResultReason implements ResultReason {

    SUCCESS(""),
    NO_VACANCY(""),
    MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED(""),
    SECONDARY_FIELDS_OF_STUDY_TESTS_NOT_PASSED("");

    String text;

    @Override
    public String text() {
        return text;
    }
}
