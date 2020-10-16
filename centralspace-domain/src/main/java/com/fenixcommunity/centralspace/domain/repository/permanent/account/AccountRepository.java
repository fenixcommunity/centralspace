package com.fenixcommunity.centralspace.domain.repository.permanent.account;

import java.util.Collection;
import java.util.List;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountCustomRepository {
    Account findByLogin(final String login);

    @Query("SELECT a FROM Account a WHERE a.login IN :logins")
    List<Account> findByLogins(@Param("logins") final Collection<String> logins,
                               final Sort sort);

    @Modifying
    @Query("UPDATE Account a SET a.login = :login WHERE a.id = :accountId")
    int updateLogin(@Param("accountId") final Long accountId,
                    @Param("login") final String login);

    @Query(value = "SELECT * FROM Account ORDER BY id",
            countQuery = "SELECT count(*) FROM Account",
            nativeQuery = true)
    Page<Account> findAllWithPagination(final Pageable pageable);
}