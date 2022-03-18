package com.hamilton.alexander.oms.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5818558114176695546L;

    public RecordNotFoundException(Class<?> entityClazz, String field, Object value) {
        super("%s %s=%s does not exist".formatted(ClassUtils.getShortName(entityClazz), field, value.toString()));
    }

}
