package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.business.CourseCreationService;
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository;
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository;
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DomainCourseCreationService implements CourseCreationService {

    FacultyRepository facultyRepository;
    CourseRepository courseRepository;

    @Override
    public CourseCreationResult createCourse(CourseProposition courseProposition) {
        return null;
    }
}
