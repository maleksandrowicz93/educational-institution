package com.github.maleksandrowicz93.educational.institution.reposiotry

import com.github.maleksandrowicz93.educational.institution.api.aggregates.CourseAggregate
import com.github.maleksandrowicz93.educational.institution.common.InMemoryRepository
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot

class InMemoryCourseRepository
        extends InMemoryRepository<CourseAggregate, CourseSnapshot, CourseId>
        implements CourseRepository {
}
