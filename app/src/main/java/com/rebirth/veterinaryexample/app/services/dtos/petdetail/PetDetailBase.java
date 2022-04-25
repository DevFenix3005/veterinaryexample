package com.rebirth.veterinaryexample.app.services.dtos.petdetail;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PetDetailBase implements Serializable {

    protected Double weight;
    protected Double height;
    protected String details;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PetDetailCreate extends PetDetailBase {
        private String petUuid;

        @NotNull
        @Pattern(regexp = "^([0-9a-z]{8})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{12})$")
        public String getPetUuid() {
            return petUuid;
        }

        @NotNull
        @Positive
        public Double getWeight() {
            return weight;
        }

        @NotNull
        @Positive
        public Double getHeight() {
            return height;
        }

        public String getDetails() {
            return details;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PetDetailUpdate extends PetDetailBase {
        private String petUuid;

        @Pattern(regexp = "^([0-9a-z]{8})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{4})-([0-9a-z]{12})$")
        public String getPetUuid() {
            return petUuid;
        }

        @Positive
        public Double getWeight() {
            return weight;
        }

        @Positive
        public Double getHeight() {
            return height;
        }

        public String getDetails() {
            return details;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class PetDetailDto extends PetDetailBase {
        private String picUrl;
        private UUID petUuid;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        private LocalDateTime createAt;
    }


}
