package com.fenixcommunity.centralspace.domain.core;

import com.fenixcommunity.centralspace.domain.model.account.Account;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class AccountEntityListener {
    @PrePersist
    public void beforePersist(Account account) {
        System.out.println("persisting account with id = " + account.getId());
    }

    @PostPersist
    public void afterPersist(Account account) {
        System.out.println("Persisted account with id = " + account.getId());
    }

    @PreUpdate
    public void beforeUpdate(Account account) {
        System.out.println("Updating account with id = " + account.getId());
    }

    @PostUpdate
    public void afterUpdate(Account account) {
        System.out.println("Updated account with id = " + account.getId());
    }

    @PreRemove
    private void beforeRemove(Account account) {
        System.out.println("Removing account with id = " + account.getId());
    }

    @PostRemove
    public void afterRemove(Account account) {
        System.out.println("Removed account with id = " + account.getId());
    }
}
