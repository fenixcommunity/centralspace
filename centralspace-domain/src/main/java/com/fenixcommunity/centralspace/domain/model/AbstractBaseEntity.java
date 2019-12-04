package com.fenixcommunity.centralspace.domain.model;

import com.fenixcommunity.centralspace.domain.converter.ZonedDateTimeJpaConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
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
