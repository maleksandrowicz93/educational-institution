package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.events.InclusionEvent;
import com.github.maleksandrowicz93.educational.institution.events.ResignationEvent;

public interface HumanResources<T extends Application, E extends InclusionEvent, R extends ResignationEvent, ID> {

    Result<E> considerApplication(T t);

    Result<R> receiveResignation(ID id);
}
