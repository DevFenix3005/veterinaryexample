package com.rebirth.veterinaryexample.app.domain.models;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Audit<ID> implements Serializable {

    /*
     * 	active bool NOT NULL DEFAULT false,
     *   create_at timestamp NOT NULL DEFAULT now(),
     *   update_at timestamp NOT NULL DEFAULT now(),
     *   "version" int8 NOT NULL DEFAULT 1,
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, insertable = false)
    protected ID id;

    @Column(name = "active")
    protected boolean active;

    @Column(name = "create_at", nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createAt;

    @Column(name = "update_at", nullable = false)
    @LastModifiedDate
    protected LocalDateTime updateAt;

    @Column(name = "version", nullable = false)
    @Version
    protected Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Audit<?> audit = (Audit<?>) o;
        return id != null && Objects.equals(id, audit.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
