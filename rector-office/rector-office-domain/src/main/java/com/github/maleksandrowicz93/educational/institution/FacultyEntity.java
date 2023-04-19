package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;

@Builder(access = PACKAGE)
@FieldDefaults(makeFinal = true)
class FacultyEntity implements Entity<FacultySnapshot, FacultyId> {

    @Getter
    FacultyId id;
    String name;
    FieldOfStudySnapshot mainFieldOfStudy;
    Set<FieldOfStudyEntity> secondaryFieldsOfStudy;

    static FacultyEntity from(FacultySnapshot source) {
        return builder()
                .id(source.id())
                .name(source.name())
                .mainFieldOfStudy(source.mainFieldOfStudy())
                .secondaryFieldsOfStudy(source.secondaryFieldsOfStudy().stream()
                        .map(FieldOfStudyEntity::from)
                        .collect(toSet()))
                .build();
    }

    @Override
    public FacultySnapshot createSnapshot() {
        return FacultySnapshot.builder()
                .id(id)
                .name(name)
                .mainFieldOfStudy(mainFieldOfStudy)
                .secondaryFieldsOfStudy(secondaryFieldsOfStudy.stream()
                        .map(FieldOfStudyEntity::createSnapshot)
                        .collect(toSet()))
                .build();
    }
}
