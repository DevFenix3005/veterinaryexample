package com.rebirth.veterinaryexample.app.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.FieldError;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PetCreateEx extends RuntimeException {
    private final List<FieldError> fieldErrors;

    public PetCreateEx(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
