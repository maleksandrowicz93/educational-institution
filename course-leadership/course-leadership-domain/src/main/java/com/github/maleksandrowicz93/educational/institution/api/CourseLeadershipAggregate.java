package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseLeadershipSnapshot;

public interface CourseLeadershipAggregate extends Aggregate<CourseLeadershipSnapshot, CourseId>, CourseLeadership {
}
