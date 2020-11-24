package com.fenixcommunity.centralspace.domain.repository.permanent.account;

import static org.hibernate.annotations.QueryHints.CACHEABLE;
import static org.hibernate.annotations.QueryHints.COMMENT;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountCustomRepository {

    @EntityGraph(attributePaths = {"address"}) // if we want to dynamically change fetchType from Lazy to Eager
    Account findByLogin(final String login);

    // not works for @EntityGraph
    <T> T findByMailOrderByMailAsc(final Class<T> dataType, final String login);

    Optional<Account> findFirstByOrderByLoginDesc();

    List<Account> findFirst10ByOrderById();

    Optional<Account> findTopByOrderByLogin();

    @Query("SELECT a FROM Account a WHERE a.login IN :logins")
    @QueryHints({
            @QueryHint(name = CACHEABLE, value = "true"),
            @QueryHint(name = COMMENT, value = "findByLogins query")
    })
    List<Account> findByLogins(@Param("logins") final Collection<String> logins,
                                                final Sort sort);

    @Query(value = "SELECT * FROM Account ORDER BY id",
            countQuery = "SELECT count(*) FROM Account",
            nativeQuery = true)
    Page<Account> findAllWithPagination(@PageableDefault(size = 5, page = 0)
                                        @SortDefault(sort = "login", direction = Sort.Direction.DESC)
                                        final Pageable pageable);

    @Modifying
    @Query("UPDATE Account a SET a.login = ?2 WHERE a.id = ?1 AND a.login = ?#{ principal?.username}")
    int updateLogin(final Long accountId,
                    final String login);

    // secure in service -> @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    long deleteByLogin(final String login);

    @Modifying
    @Query("DELETE from Account a WHERE a.id = :accountId AND ?#{ principal?.credentialsNonExpired} = true")
    long deleteByIdWhereLoginIsEmpty(@Param("accountId") final Long accountId);
}