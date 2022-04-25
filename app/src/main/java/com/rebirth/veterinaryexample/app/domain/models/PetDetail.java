package com.rebirth.veterinaryexample.app.domain.models;

import com.rebirth.veterinaryexample.app.domain.enums.PetStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "pet_detail", schema = "app")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "pet_detail_id"))
})
public class PetDetail extends Audit<Long> {
    /*
     * weight float8 NOT NULL,
     * height float8 NOT NULL,
     * birthday date NOT NULL,
     * primary_color varchar(20) NOT NULL,
     * secundary_color2 varchar(20) NULL,
     * details text NULL,
     * pet_id int8 NOT NULL
     * */

    @Column(name = "weight", nullable = false, columnDefinition = "float8")
    private Double weight;

    @Column(name = "height", nullable = false, columnDefinition = "float8")
    private Double height;

    @Column(name = "details", nullable = true, columnDefinition = "varchar")
    private String details;

    @CreatedBy
    @Column(name = "create_by", nullable = false)
    private String createBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    @ToString.Exclude
    private Pet pet;


}
