package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyName
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

import static FieldOfStudyUtils.fieldOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.MAIN_FIELD_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.SECONDARY_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.secondaryFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.BASIC_THRESHOLD
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.facultyManagementThresholds
import static java.util.stream.Collectors.toSet

class FacultyUtils {

    static final def FACULTY_NAME = "Mechanical"

    private FacultyUtils() {}

    static def facultySetup(EducationalInstitutionId educationalInstitutionId) {
        FacultySetup.builder()
                .name(FACULTY_NAME)
                .educationalInstitutionId(educationalInstitutionId)
                .mainFieldOfStudyName(mainFieldOfStudyName())
                .secondaryFieldsOfStudyNames(secondaryFieldsOfStudyNames())
                .facultyManagementThresholds(facultyManagementThresholds())
                .build()
    }

    private static def mainFieldOfStudyName() {
        return new FieldOfStudyName(MAIN_FIELD_OF_STUDY)
    }

    private static def secondaryFieldsOfStudyNames() {
        SECONDARY_FIELDS_OF_STUDY.stream()
                .map { new FieldOfStudyName(it) }
                .collect(toSet())
    }

    static def newFaculty() {
        newFacultyConfiguredWith(facultyManagementThresholds())
    }

    static def newFaculty(Threshold maximumProfessorCourses) {
        newFacultyConfiguredWith(facultyManagementThresholds(maximumProfessorCourses))
    }

    private static def newFacultyConfiguredWith(FacultyManagementThresholds thresholds) {
        def facultyId = new FacultyId(UUID.randomUUID())
        FacultySnapshot.builder()
                .id(facultyId)
                .name(FACULTY_NAME)
                .educationalInstitutionId(new EducationalInstitutionId(UUID.randomUUID()))
                .facultyManagementThresholds(thresholds)
                .mainFieldOfStudy(fieldOfStudy(MAIN_FIELD_OF_STUDY, facultyId))
                .secondaryFieldsOfStudy(secondaryFieldsOfStudy(facultyId))
                .professors(Set.of())
                .students(Set.of())
                .courses(Set.of())
                .build()
    }

    static def basicCourseProposition(ProfessorSnapshot hired, FacultyId facultyId) {
        courseProposition(hired, secondaryFieldsOfStudy(facultyId))
    }

    static def courseProposition(ProfessorSnapshot hired, Set<FieldOfStudySnapshot> fieldsOfStudy) {
        CourseProposition.builder()
                .name("Machines Construction Fundamentals")
                .professorId(hired.id())
                .facultyId(hired.facultyId())
                .fieldsOfStudy(fieldsOfStudy)
                .maximumNumberOfStudents(BASIC_THRESHOLD)
                .build()
    }
}
