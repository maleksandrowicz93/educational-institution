package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeRepository;
import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeService;
import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.events.FacultyCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
class RectorOfficeDomainService implements RectorOfficeService {

    RectorOfficeRepository rectorOfficeRepository;
    EventsPublisher eventsPublisher;

    @Override
    public Result<FacultyCreatedEvent> createFaculty(RectorOfficeId rectorOfficeId, FacultySetup facultySetup) {
        Optional<RectorOfficeAggregate> maybeRectorOffice = rectorOfficeRepository.findById(rectorOfficeId);
        Result<FacultyCreatedEvent> result = maybeRectorOffice
                .map(rectorOffice -> rectorOffice.createFaculty(facultySetup))
                .orElseGet(Result::notFound);
        maybeRectorOffice.ifPresent(rectorOffice -> result.value()
                .ifPresent(event -> {
                    rectorOfficeRepository.save(rectorOffice);
                    eventsPublisher.publish(event);
                }));
        return result;
    }
}
