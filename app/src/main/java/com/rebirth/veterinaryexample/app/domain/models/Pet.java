package com.rebirth.veterinaryexample.app.domain.models;

import com.rebirth.veterinaryexample.app.domain.enums.PetGender;
import com.rebirth.veterinaryexample.app.domain.enums.PetStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "pets", schema = "app")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "pet_id"))
})
public class Pet extends Audit<Long> {

    @Column(name = "pet_uuid", nullable = false, updatable = false, unique = true)
    @NaturalId(mutable = false)
    private UUID uuid;

    @Column(name = "pet_name", nullable = false)
    private String name;

    @CreatedBy
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "pet_document", nullable = false)
    private String petDocument;

    @Column(name = "pet_pic", nullable = true)
    private String petPic;

    @Column(name = "birthday", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime birthday;

    @Column(name = "primary_color", nullable = false, columnDefinition = "varchar")
    private String primaryColor;

    @Column(name = "secundary_color", nullable = true, columnDefinition = "varchar")
    private String secundaryColor;

    @Column(name = "gender", nullable = false, columnDefinition = "petgender")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private PetGender gender;

    @Column(name = "status", nullable = false, columnDefinition = "petstatus")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private PetStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    @ToString.Exclude
    private Breed breed;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PetDetail> detail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pet pet = (Pet) o;
        return getId() != null && Objects.equals(getId(), pet.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
