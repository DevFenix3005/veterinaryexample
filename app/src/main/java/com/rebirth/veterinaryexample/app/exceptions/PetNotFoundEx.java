package com.rebirth.veterinaryexample.app.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class PetNotFoundEx extends RuntimeException {

    private final UUID petUUID;

    public PetNotFoundEx(UUID petUUID) {
        super("Pet not found with the uuid " + petUUID.toString());
        this.petUUID = petUUID;
    }
}
