package com.fenixcommunity.centralspace.Jpa;

import com.fenixcommunity.centralspace.Converter.ZonedDateTimeJpaConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class AbstractBaseEntity {
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
