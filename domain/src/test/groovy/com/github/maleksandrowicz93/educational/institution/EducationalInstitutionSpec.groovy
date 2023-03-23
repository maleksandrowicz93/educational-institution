package com.github.maleksandrowicz93.educational.institution

import spock.lang.Specification

class EducationalInstitutionSpec extends Specification {

    def "faculty may be created"() {
        given: "Educational Institution without any faculty"
        and: "new faculty to be created"
        when: "the faculty is created"
        then: "should be created successfully"
    }

    def "professor matching all requirements may be employed"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "professor's application matching all requirements"
        when: "professor applies for hiring"
        then: "professor should be employed"
    }

    def "professor not matching requirements should not be employed"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "professor's application not matching all requirements"
        when: "professor applies for hiring"
        then: "professor should not be employed"
    }

    def "professor should not be employed when no vacancy at faculty"() {
        given: "Educational Institution with no vacancy at a faculty"
        and: "professor's application matching all requirements"
        when: "professor applies for hiring"
        then: "professor should not be employed"
    }

    def "professor may resign from employment"() {
        given: "Educational Institution with a professor hired at a faculty"
        when: "professor resigns from employment"
        then: "all courses led by him become vacated"
        and: "professor is marked as inactive"
        and: "external system should be notified"
    }

    def "student matching all requirements may be enrolled"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "student's application matching all requirements"
        when: "student applies for enrollment"
        then: "student should be enrolled"
    }

    def "student not matching requirements should not be enrolled"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "student's application not matching requirements"
        when: "student applies for enrollment"
        then: "student should not be enrolled"
    }

    def "student should not be enrolled when no vacancy at faculty"() {
        given: "Educational Institution with no vacancy at a faculty"
        and: "student's application matching all requirements"
        when: "student applies for enrollment"
        then: "student should not be enrolled"
    }

    def "student may resign from enrollment"() {
        given: "Educational Institution with a student enrolled at a faculty"
        when: "student resigns from enrollment"
        then: "all courses student is enrolled for become vacated"
        and: "student is marked as inactive"
        and: "external system should be notified"
    }

    def "professor with capacity may create a course matching all requirements within a faculty"() {
        given: "Educational Institution with a professor with capacity hired at a faculty"
        and: "course matching all requirements to be created"
        when: "professor creates course"
        then: "course should be created"
    }

    def "professor with capacity should not create a course not matching requirements within a faculty"() {
        given: "Educational Institution with a professor with capacity hired at a faculty"
        and: "course not matching requirements to be created"
        when: "professor creates course"
        then: "course should not be created"
    }

    def "professor with no capacity should not create a course matching all requirements within a faculty"() {
        given: "Educational Institution with a professor with no capacity hired at a faculty"
        and: "course matching all requirements to be created"
        when: "professor creates course"
        then: "course should not be created"
    }

    def "professor may resign from leading the course"() {
        given: "Educational Institution with a professor leading the course at a faculty"
        when: "professor resigns from leading the course"
        then: "the course becomes vacated"
        and: "external system should be notified"
    }

    def "vacated course may be overtaken by a professor matching all requirements"() {
        given: "Educational Institution with the vacated course within a faculty"
        and: "employed professor matching all requirements"
        when: "this professor overtakes the course"
        then: "course should be overtaken"
    }

    def "vacated course should not be overtaken by a professor not matching requirements"() {
        given: "Educational Institution with the vacated course within a faculty"
        and: "employed professor not matching requirements"
        when: "this professor overtakes the course"
        then: "course should not be overtaken"
    }

    def "vacated course should not be overtaken by a professor from other faculty"() {
        given: "Educational Institution with 2 faculties"
        and: "vacated course within first faculty"
        and: "professor matching all requirements employed in second faculty"
        when: "professor overtakes the curse"
        then: "course should not be overtaken"
    }

    def "student may enroll for the vacated course"() {
        given: "Educational Institution with the vacated course within a faculty"
        and: "student enrolled in the faculty"
        when: "student enrolls for the course"
        then: "student should be enrolled"
    }

    def "student should not be enrolled for the full course"() {
        given: "Educational Institution with the full course within a faculty"
        and: "student enrolled in the faculty"
        when: "student enrolls for the course"
        then: "student should not be enrolled"
    }

    def "student should not be enrolled for the vacated course from other faculty"() {
        given: "Educational Institution with 2 faculties"
        and: "vacated course within first faculty"
        and: "student enrolled in second faculty"
        when: "student enrolls for the course"
        then: "student should not be enrolled"
    }

    def "course with too many vacancies may be closed"() {
        given: "Educational Institution with the course within a faculty"
        and: "too many vacancies within the course"
        when: "professor closes the course"
        then: "the course should be closed"
        and: "external system should be notified"
    }

    def "course with enough number of enrolled students should not be closed"() {
        given: "Educational Institution with the course within a faculty"
        and: "enough value of students are enrolled for the course"
        when: "professor closes the course"
        then: "the course should not be closed"
    }
}
