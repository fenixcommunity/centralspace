package com.fenixcommunity.centralspace.domain.repository.permanent.account;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;

public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @PersistenceContext(synchronization = SynchronizationType.SYNCHRONIZED)
    private EntityManager entityManager;

    @Override
    public List<Account> findAccountsByEmails(final Set<String> emails) {
        if (isEmpty(emails)) {
            return null;
        }
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Account> query = cb.createQuery(Account.class);
        final Root<Account> account = query.from(Account.class);

        final Path<String> emailPath = account.get("mail");

        final List<Predicate> predicates = new ArrayList<>();
        emails.forEach(email -> predicates.add(cb.like(emailPath, email)));
        query.select(account)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
