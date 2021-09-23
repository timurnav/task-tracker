package com.attlasiam.tracker.service.exceptions;

public class BaseTrackerException extends RuntimeException {

    public BaseTrackerException() {
    }

    public BaseTrackerException(String message) {
        super(message);
    }

    public BaseTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseTrackerException(Throwable cause) {
        super(cause);
    }
}
