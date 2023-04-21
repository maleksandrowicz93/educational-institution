package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.domain.service.FacultyManagementService;
import com.github.maleksandrowicz93.educational.institution.api.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DomainFacultyManagementService implements FacultyManagementService {

    FacultyRepository facultyRepository;
    EventsPublisher eventsPublisher;

    @Override
    public Result<ProfessorSnapshot> resignFromEmployment(ProfessorSnapshot professorSnapshot) {
        return null;
    }

    @Override
    public Result<StudentSnapshot> resignFromEnrollment(StudentSnapshot studentSnapshot) {
        return null;
    }
}
