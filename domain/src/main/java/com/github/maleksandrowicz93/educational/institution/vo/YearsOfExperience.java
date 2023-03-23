package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.exception.InvalidYearsOfExperienceException;

public record YearsOfExperience(int amount) {
    public YearsOfExperience {
        if (amount < 0) {
            throw new InvalidYearsOfExperienceException();
        }
    }
}
