package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Entity;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class FieldOfStudy implements Entity<FieldOfStudySnapshot, FieldOfStudyId> {

    @Getter
    FieldOfStudyId id;
    String name;
    FacultyId facultyId;

    static FieldOfStudy from(FieldOfStudySnapshot snapshot) {
        return builder()
                .id(snapshot.id())
                .name(snapshot.name())
                .facultyId(snapshot.facultyId())
                .build();
    }

    @Override
    public FieldOfStudySnapshot createSnapshot() {
        return FieldOfStudySnapshot.builder()
                .id(id)
                .name(name)
                .facultyId(facultyId)
                .build();
    }
}
