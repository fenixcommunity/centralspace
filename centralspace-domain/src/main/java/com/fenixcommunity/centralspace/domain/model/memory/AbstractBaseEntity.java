package com.fenixcommunity.centralspace.domain.model.memory;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fenixcommunity.centralspace.domain.converter.ZonedDateTimeJpaConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@MappedSuperclass @Getter @Setter @FieldDefaults(level = PRIVATE)
public abstract class AbstractBaseEntity implements Serializable {
    // Store always in UTC
    // Read from database (stored in UTC) and return with the system default.

    @Convert(converter = ZonedDateTimeJpaConverter.class)
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Convert(converter = ZonedDateTimeJpaConverter.class)
    @Column(name = "update_date", nullable = false)
    private ZonedDateTime updateDate;

    @PrePersist
    private void prePersist() {
        creationDate = ZonedDateTime.now();
        updateDate = ZonedDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updateDate = ZonedDateTime.now();
    }


}
