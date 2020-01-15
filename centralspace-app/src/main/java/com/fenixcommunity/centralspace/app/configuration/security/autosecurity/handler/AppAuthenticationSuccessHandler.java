package com.fenixcommunity.centralspace.app.configuration.security.autosecurity.handler;

import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.fenixcommunity.centralspace.utilities.web.WebTool.getPreviousPageByRequest;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final String forwardUrl;

    public AppAuthenticationSuccessHandler(final String forwardUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), () -> "'" + forwardUrl + "' is not a valid forward URL");
//        todo isTrue
        this.forwardUrl = forwardUrl;
    }

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
            throws IOException, ServletException {
        final Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect(forwardUrl);
//          response.sendRedirect("/admin.html");
//          todo implement
        } else {
            response.sendRedirect(getPreviousPageByRequest(request).orElse("/login"));
        }
    }
}