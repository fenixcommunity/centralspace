package com.fenixcommunity.centralspace.domain.repository;

import com.fenixcommunity.centralspace.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
