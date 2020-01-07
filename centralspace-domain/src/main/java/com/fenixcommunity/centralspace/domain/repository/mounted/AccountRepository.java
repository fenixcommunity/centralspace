package com.fenixcommunity.centralspace.domain.repository.mounted;

import com.fenixcommunity.centralspace.domain.model.mounted.account.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByLogin(String login);
}