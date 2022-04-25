package com.rebirth.veterinaryexample.app.services.dtos.pets;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PetBase implements Serializable {

    protected String name;
    protected String gender;
    protected String status;
    protected LocalDateTime birthday;
    protected String primaryColor;
    protected String secundaryColor;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PetCreate extends PetBase {

        protected String breedUuid;

        @NotNull
        @Length(min = 3, max = 40)
        public String getName() {
            return this.name;
        }

        @NotNull
        @Pattern(regexp = "^(Female|Male)$")
        public String getGender() {
            return this.gender;
        }

        @NotBlank
        @Pattern(regexp = "^(OK|Caution|Risk|Critical)$")
        public String getStatus() {
            return status;
        }

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        public LocalDateTime getBirthday() {
            return birthday;
        }

        @NotNull
        @NotBlank
        public String getPrimaryColor() {
            return primaryColor;
        }

        @NotBlank
        public String getSecundaryColor() {
            return secundaryColor;
        }

        @NotNull
        @Pattern(regexp = "^([0-9a-z]{8})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{12})$")
        public String getBreedUuid() {
            return this.breedUuid;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PetUpdate extends PetBase {

        protected String breedUuid;

        @Length(min = 3, max = 40)
        public String getName() {
            return this.name;
        }

        @Pattern(regexp = "^(Female|Male)$")
        public String getGender() {
            return this.gender;
        }

        @Pattern(regexp = "^([0-9a-z]{8})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{12})$")
        public String getBreedUuid() {
            return this.breedUuid;
        }

        public LocalDateTime getBirthday() {
            return birthday;
        }

        public String getPrimaryColor() {
            return primaryColor;
        }

        public String getSecundaryColor() {
            return secundaryColor;
        }

        @Pattern(regexp = "^(OK|Caution|Risk|Critical)$")
        public String getStatus() {
            return status;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class PetDto extends PetBase {
        private UUID uuid;
        private String breed;
    }

}
