package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;

@Builder(access = PACKAGE)
@FieldDefaults(makeFinal = true)
class FieldOfStudyEntity implements Entity<FieldOfStudySnapshot, FieldOfStudyId> {

    @Getter
    FieldOfStudyId id;
    String name;

    static FieldOfStudyEntity from(FieldOfStudySnapshot source) {
        return builder()
                .id(source.id())
                .name(source.name())
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
