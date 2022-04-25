package com.rebirth.veterinaryexample.app.services.dtos.breeds;

import org.hibernate.validator.constraints.Length;

public class BreedUpdate extends BreedBase {

    @Length(min = 1, max = 50)
    public String getName() {
        return this.name;
    }

}
