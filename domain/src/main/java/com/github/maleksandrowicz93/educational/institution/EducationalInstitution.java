package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher;
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSetup;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class EducationalInstitution implements EducationalInstitutionAggregate {

    EducationalInstitutionId id;
    EducationalInstitutionSetup setup;
    Set<Faculty> faculties;
    EventsPublisher eventsPublisher;

    static EducationalInstitution from(EducationalInstitutionSnapshot snapshot, EventsPublisher eventsPublisher) {
        return builder()
                .id(snapshot.id())
                .setup(snapshot.setup())
                .faculties(snapshot.faculties().stream()
                        .map(Faculty::from)
                        .collect(toSet()))
                .eventsPublisher(eventsPublisher)
                .build();
    }

    @Override
    public EducationalInstitutionSnapshot createSnapshot() {
        return EducationalInstitutionSnapshot.builder()
                .id(id)
                .setup(setup)
                .faculties(faculties.stream()
                        .map(Faculty::createSnapshot)
                        .collect(toSet()))
                .build();
    }

    @Override
    public FacultySnapshot createFaculty(FacultySetup facultySetup) {
        return null;
    }

    private static FieldOfStudySnapshot createFieldOfStudySnapshot(FacultyId facultyId, String name) {
        return FieldOfStudySnapshot.builder()
                .id(new FieldOfStudyId(UUID.randomUUID()))
                .name(name)
                .facultyId(facultyId)
                .build();
    }

    @Override
    public Optional<ProfessorSnapshot> considerHiring(ProfessorApplication professorApplication) {
        return Optional.empty();
    }

    @Override
    public ProfessorSnapshot receiveHiringResignation(ProfessorId professorId) {
        return null;
    }

    @Override
    public Optional<StudentSnapshot> considerEnrollment(StudentApplication studentApplication) {
        return Optional.empty();
    }

    @Override
    public StudentSnapshot receiveEnrollmentResignation(StudentId studentId) {
        return null;
    }

    @Override
    public Optional<CourseSnapshot> considerCourseCreation(CourseProposition courseProposition) {
        return Optional.empty();
    }

    @Override
    public CourseSnapshot receiveCourseLeadingResignation(CourseId courseId) {
        return null;
    }

    @Override
    public Optional<CourseSnapshot> considerCourseOvertaking(CourseOvertakingApplication courseOvertakingApplication) {
        return Optional.empty();
    }

    @Override
    public Optional<CourseSnapshot> considerCourseEnrollment(CourseEnrollmentApplication courseEnrollmentApplication) {
        return Optional.empty();
    }

    @Override
    public Optional<CourseSnapshot> considerClosingCourse(CourseId courseId) {
        return Optional.empty();
    }
}
