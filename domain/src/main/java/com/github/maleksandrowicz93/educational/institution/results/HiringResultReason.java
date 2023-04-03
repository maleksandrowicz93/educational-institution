package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum HiringResultReason implements ResultReason {

    SUCCESS(""),
    NOT_MATCHED_FIELDS_OF_STUDY(""),
    TOO_LITTLE_EXPERIENCE(""),
    NO_VACANCY("");

    String text;

    @Override
    public String text() {
        return text;
    }
}
