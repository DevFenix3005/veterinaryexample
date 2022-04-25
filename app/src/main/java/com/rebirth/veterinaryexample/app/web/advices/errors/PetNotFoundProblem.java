package com.rebirth.veterinaryexample.app.web.advices.errors;

import java.util.UUID;

public class PetNotFoundProblem extends BadRequestEx {
    public PetNotFoundProblem(UUID uuid) {
        super("Pet not found " + uuid.toString(), "pet", "uuid");
    }
}
