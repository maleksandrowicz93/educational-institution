package com.github.maleksandrowicz93.educational.institution.result.reasons;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum EnrollmentResultReason implements ResultReason {

    NO_VACANCY("No vacancy at faculty."),
    MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED("Main field of study test not passed."),
    SECONDARY_FIELDS_OF_STUDY_TESTS_NOT_PASSED("Secondary fields of study tests not passed.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
