package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record StudentApplication(
        PersonalData personalData,
        TestResult mainTestResult,
        TestResult secondaryTestResult,
        FacultyId facultyId
) {
}
