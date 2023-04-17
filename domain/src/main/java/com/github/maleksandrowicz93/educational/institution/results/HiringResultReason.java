package com.github.maleksandrowicz93.educational.institution.results;

import com.github.maleksandrowicz93.educational.institution.common.ResultReason;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public enum HiringResultReason implements ResultReason {

    FIELDS_OF_STUDY_NOT_MATCHED("Field of study not matched."),
    TOO_LITTLE_EXPERIENCE("Too little experience."),
    NO_VACANCY("No vacancy at faculty.");

    String text;

    @Override
    public String text() {
        return text;
    }
}
