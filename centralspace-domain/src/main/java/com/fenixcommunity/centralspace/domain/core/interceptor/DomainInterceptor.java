package com.fenixcommunity.centralspace.domain.core.interceptor;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class DomainInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        if (entity instanceof Account) {
            log.info("Entity saved. Id: " + id);
        }
        return false;
    }

    @Override
    public boolean onLoad(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        if (entity instanceof Account) {
            log.info("Entity loaded. Id: " + id);
        }
        return false;
    }
}
