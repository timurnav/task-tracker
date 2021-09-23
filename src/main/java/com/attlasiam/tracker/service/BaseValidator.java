package com.attlasiam.tracker.service;

import com.attlasiam.tracker.service.exceptions.ValidationException;

public class BaseValidator {

    protected void validate(boolean condition, String errorMessage) {
        if (!condition) {
            throw new ValidationException(errorMessage);
        }
    }
}
