package com.fenixcommunity.centralspace.domain.repository.mounted;

import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByLogin(String login);
}