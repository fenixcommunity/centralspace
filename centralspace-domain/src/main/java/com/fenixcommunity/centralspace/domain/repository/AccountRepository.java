package com.fenixcommunity.centralspace.domain.repository;

import com.fenixcommunity.centralspace.domain.model.account.Account;
import org.springframework.data.repository.CrudRepository;




public interface AccountRepository extends CrudRepository<Account, Long> {

}