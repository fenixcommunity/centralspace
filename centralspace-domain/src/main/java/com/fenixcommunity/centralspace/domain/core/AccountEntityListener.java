package com.fenixcommunity.centralspace.domain.core;

import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import lombok.experimental.FieldDefaults;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountEntityListener {
    @PrePersist
    public void beforePersist(Account account) {
        System.out.println("persisting account with id = " + account.getId());
    }

    @PostPersist
    public void afterPersist(final Account account) {
        System.out.println("Persisted account with id = " + account.getId());
    }

    @PreUpdate
    public void beforeUpdate(final Account account) {
        System.out.println("Updating account with id = " + account.getId());
    }

    @PostUpdate
    public void afterUpdate(final Account account) {
        System.out.println("Updated account with id = " + account.getId());
    }

    @PreRemove
    private void beforeRemove(final Account account) {
        System.out.println("Removing account with id = " + account.getId());
    }

    @PostRemove
    public void afterRemove(final Account account) {
        System.out.println("Removed account with id = " + account.getId());
    }
}
