package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.domain.service.CourseCreationService;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository;
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DomainCourseCreationService implements CourseCreationService {

    FacultyRepository facultyRepository;
    CourseRepository courseRepository;

    @Override
    public Result<CourseSnapshot> createCourse(CourseProposition courseProposition) {
        return null;
    }
}
