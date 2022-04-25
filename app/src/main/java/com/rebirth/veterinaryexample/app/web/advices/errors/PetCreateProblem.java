package com.rebirth.veterinaryexample.app.web.advices.errors;

import java.util.Map;

public class PetCreateProblem extends BadRequestEx {

    public PetCreateProblem(Map<String,Object> errors) {
        super("Pet create no valid",  errors);
    }

}
