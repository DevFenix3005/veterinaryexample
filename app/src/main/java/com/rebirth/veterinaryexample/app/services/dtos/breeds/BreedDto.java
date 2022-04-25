package com.rebirth.veterinaryexample.app.services.dtos.breeds;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BreedDto extends BreedBase {

    protected UUID uuid;
}
