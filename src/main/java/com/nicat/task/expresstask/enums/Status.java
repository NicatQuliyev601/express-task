package com.nicat.task.expresstask.enums;

public enum Status {
    CANCELED (0),
    ERROR (1),
    IDLE (2),
    PENDING (3),
    SUCCESS (4);

    private int statusCode;

    Status(int statusCode) {
        this.statusCode = statusCode;
    }
}
