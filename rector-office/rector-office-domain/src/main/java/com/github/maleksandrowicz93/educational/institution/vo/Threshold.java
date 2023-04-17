package com.github.maleksandrowicz93.educational.institution.vo;

public record Threshold(int criticalPoint) {
    public Threshold {
        if (criticalPoint < 0) {
            throw new IllegalArgumentException("Threshold should be greater or equal to 0.");
        }
    }
}
