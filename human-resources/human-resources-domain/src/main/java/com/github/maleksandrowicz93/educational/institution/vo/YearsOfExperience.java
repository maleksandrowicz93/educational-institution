package com.github.maleksandrowicz93.educational.institution.vo;

public record YearsOfExperience(int amount) {
    public YearsOfExperience {
        if (amount < 0) {
            throw new IllegalArgumentException("Years of experience should be greater or equal to 0.");
        }
    }
}
