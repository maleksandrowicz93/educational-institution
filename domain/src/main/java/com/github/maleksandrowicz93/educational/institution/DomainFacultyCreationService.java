package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.domain.service.FacultyCreationService;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.repository.EducationalInstitutionRepository;
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DomainFacultyCreationService implements FacultyCreationService {

    EducationalInstitutionRepository educationalInstitutionRepository;
    FacultyRepository facultyRepository;

    @Override
    public Result<FacultySnapshot> createFaculty(FacultySetup facultySetup) {
        return null;
    }
}
