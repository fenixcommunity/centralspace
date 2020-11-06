package com.fenixcommunity.centralspace.domain.repository.permanent.account;

import java.util.Collection;
import java.util.List;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountCustomRepository {

    @EntityGraph(attributePaths = {"address"}) // if we want to dynamically change fetchType from Lazy to Eager
    Account findByLogin(final String login);

    @Query("SELECT a FROM Account a WHERE a.login IN :logins")
    List<Account> findByLogins(@Param("logins") final Collection<String> logins,
                               final Sort sort);

    @Query(value = "SELECT * FROM Account ORDER BY id",
            countQuery = "SELECT count(*) FROM Account",
            nativeQuery = true)
    Page<Account> findAllWithPagination(@PageableDefault(size = 5, page = 0)
                                        @SortDefault(sort = "login", direction = Sort.Direction.DESC)
                                        final Pageable pageable);

    @Modifying
    @Query("UPDATE Account a SET a.login = ?2 WHERE a.id = ?1")
    int updateLogin(final Long accountId,
                    final String login);

    long deleteByLogin(final String login);

    @Modifying
    @Query("DELETE from Account a WHERE a.id = :accountId")
    long deleteByIdWhereLoginIsEmpty(@Param("accountId") final Long accountId);
}