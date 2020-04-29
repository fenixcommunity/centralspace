package com.fenixcommunity.centralspace.domain.core;

import static lombok.AccessLevel.PRIVATE;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class AccountEntityListener {
    @PrePersist
    public void beforePersist(Account account) {
        log.info("persisting account with id = " + account.getId());
    }

    @PostPersist
    public void afterPersist(final Account account) {
        log.info("Persisted account with id = " + account.getId());
    }

    @PreUpdate
    public void beforeUpdate(final Account account) {
        log.info("Updating account with id = " + account.getId());
    }

    @PostUpdate
    public void afterUpdate(final Account account) {
        log.info("Updated account with id = " + account.getId());
    }

    @PreRemove
    private void beforeRemove(final Account account) {
        log.info("Removing account with id = " + account.getId());
    }

    @PostRemove
    public void afterRemove(final Account account) {
        log.info("Removed account with id = " + account.getId());
    }
}
