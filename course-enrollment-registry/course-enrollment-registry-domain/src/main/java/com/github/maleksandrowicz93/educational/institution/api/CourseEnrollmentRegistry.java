package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.CourseClosedEvent;
import com.github.maleksandrowicz93.educational.institution.events.StudentEnrolledForCourseEvent;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;

public interface CourseEnrollmentRegistry {

    Result<StudentEnrolledForCourseEvent> considerCourseEnrollment(StudentId studentId);

    Result<CourseClosedEvent> considerClosingCourse();
}
