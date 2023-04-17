package com.github.maleksandrowicz93.educational.institution.reposiotry

import com.github.maleksandrowicz93.educational.institution.common.InMemoryRepository
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot

class InMemoryFacultyRepository extends InMemoryRepository<FacultySnapshot, FacultyId> implements FacultyRepository {
}