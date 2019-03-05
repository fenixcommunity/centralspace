package com.fenixcommunity.centralspace.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
