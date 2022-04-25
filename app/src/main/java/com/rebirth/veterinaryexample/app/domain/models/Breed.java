package com.rebirth.veterinaryexample.app.domain.models;

import com.rebirth.veterinaryexample.app.domain.enums.BreedSize;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@Entity
@Table(name = "breeds", schema = "app")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "breed_id"))
})
public class Breed extends Audit<Long> {

    @NonNull
    @NaturalId(mutable = false)
    @Column(name = "breed_uuid", nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @NonNull
    @Column(name = "breed_name", nullable = false)
    private String name;

    @Column(name = "breed_size", columnDefinition = "breedsize")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private BreedSize size;

    @Column(name = "breed_description")
    private String description;

    @OneToMany(mappedBy = "breed", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Pet> pets;

    @CreatedBy
    @Column(name = "create_by", nullable = false)
    private String createBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Breed breed = (Breed) o;
        return getId() != null && Objects.equals(getId(), breed.getId()) && Objects.equals(getUuid(), breed.getUuid());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
