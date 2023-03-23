package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.exception.InvalidThresholdException;

public record Threshold(int criticalPoint) {
    public Threshold {
        if (criticalPoint < 0) {
            throw new InvalidThresholdException();
        }
    }
}
