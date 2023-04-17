package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.FacultyCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.Faculty;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class RectorOfficeAggregateRoot implements RectorOfficeAggregate {

    final RectorOfficeSnapshot source;
    @Getter
    final RectorOfficeId id;
    final Set<Faculty> faculties;

    static RectorOfficeAggregate from(RectorOfficeSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .faculties(source.faculties())
                .build();
    }

    @Override
    public RectorOfficeSnapshot createSnapshot() {
        return source.toBuilder()
                .faculties(Set.copyOf(faculties))
                .build();
    }

    @Override
    public Result<FacultyCreatedEvent> createFaculty(FacultySetup facultySetup) {
        return null;
    }
}
