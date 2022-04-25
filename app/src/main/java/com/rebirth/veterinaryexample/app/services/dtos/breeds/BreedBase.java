package com.rebirth.veterinaryexample.app.services.dtos.breeds;

import lombok.Data;

import java.io.Serializable;

@Data
public class BreedBase implements Serializable {

    protected String name;
    protected String size;
    protected String description;

}
