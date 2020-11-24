package com.fenixcommunity.centralspace.app.configuration.caching;

import static com.fenixcommunity.centralspace.utilities.common.Var.UNDERSCORE;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

public class AppKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(final Object target, final Method method, final Object... params) {
        return target.getClass().getSimpleName() + UNDERSCORE
                + method.getName() + UNDERSCORE
                + StringUtils.arrayToDelimitedString(params, UNDERSCORE);
    }
}
