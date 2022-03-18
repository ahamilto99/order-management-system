package com.hamilton.alexander.oms.config.exception;

import java.io.Serializable;

public record ErrorMessage(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) implements Serializable {

}
