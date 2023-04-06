package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository;
import com.github.maleksandrowicz93.educational.institution.repository.EducationalInstitutionRepository;
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository;
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseLeadingResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.FacultyCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class EducationalInstitutionService implements EducationalInstitutionDomainService {

    EducationalInstitutionRepository educationalInstitutionRepository;
    FacultyRepository facultyRepository;
    CourseRepository courseRepository;
    EventsPublisher eventsPublisher;

    //START

    @Override
    public FacultyCreationResult createFaculty(FacultySetup facultySetup) {
        return null;
    }


    @Override
    public CourseCreationResult createCourse(CourseProposition courseProposition) {
        return null;
    }

    @Override
    public EmploymentResignationResult resignFromEmployment(ProfessorSnapshot professorSnapshot) {
        return null;
    }

    @Override
    public EnrollmentResignationResult resignFromEnrollment(StudentSnapshot studentSnapshot) {
        return null;
    }

    @Override
    public CourseLeadingResignationResult resignFromLeadingCourse(CourseId courseId) {
        return null;
    }

    @Override
    public CourseClosingResult closeCourse(CourseId courseId) {
        return null;
    }
}
