package com.rebirth.veterinaryexample.app.services.dtos.breeds;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class BreedCreate extends BreedBase {

    @NotNull
    @Length(min = 1, max = 50)
    public String getName() {
        return this.name;
    }

    @Pattern(regexp = "^(Extra_Large|Large|Medium|Small|Tiny)$")
    public String getSize() {
        return size;
    }

    @Length(max = 300)
    public String getDescription() {
        return description;
    }
}
