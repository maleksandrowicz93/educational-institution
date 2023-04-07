package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.business.FacultyManagementService;
import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository;
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
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
    public EmploymentResignationResult resignFromEmployment(ProfessorSnapshot professorSnapshot) {
        return null;
    }

    @Override
    public EnrollmentResignationResult resignFromEnrollment(StudentSnapshot studentSnapshot) {
        return null;
    }
}
