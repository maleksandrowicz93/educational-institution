package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
class FieldOfStudy implements Entity<FieldOfStudySnapshot> {

    FieldOfStudyId id;
    Name name;

    static FieldOfStudy from(FieldOfStudySnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .build();
    }

    @Override
    public FieldOfStudySnapshot createSnapshot() {
        return FieldOfStudySnapshot.builder()
                .id(id)
                .name(name)
                .build();
    }
}
