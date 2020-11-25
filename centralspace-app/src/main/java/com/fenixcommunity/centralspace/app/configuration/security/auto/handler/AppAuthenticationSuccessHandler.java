package com.fenixcommunity.centralspace.app.configuration.security.auto.handler;

import static com.fenixcommunity.centralspace.utilities.web.WebTool.getPreviousPageByRequest;
import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fenixcommunity.centralspace.app.configuration.security.auto.LoginAttemptService;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final LoginAttemptService loginAttemptService;
    private final PasswordEncoder encoder;
    private final int sessionTimeout;
    private final String forwardUrl;

    public AppAuthenticationSuccessHandler(final LoginAttemptService loginAttemptService,
                                           final PasswordEncoder encoder,
                                           final int sessionTimeout,
                                           final String forwardUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), () -> "'" + forwardUrl + "' is not a valid forward URL");
        this.loginAttemptService = loginAttemptService;
        this.encoder = encoder;
        this.forwardUrl = forwardUrl;
        this.sessionTimeout = sessionTimeout;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        if (authentication instanceof UsernamePasswordAuthenticationToken && authentication.getCredentials() != null) {
            final CharSequence clearTextPass = (CharSequence) authentication.getCredentials();
            final String newPasswordHash = encoder.encode(clearTextPass);

            ((UsernamePasswordAuthenticationToken) authentication).eraseCredentials();
        }

        final WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
        loginAttemptService.loginSucceeded(webAuthenticationDetails.getRemoteAddress());

        final Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        request.getSession(false).setMaxInactiveInterval(sessionTimeout);
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect(forwardUrl);
//todo          response.sendRedirect("/admin.html");
        } else {
            response.sendRedirect(getPreviousPageByRequest(request).orElse(forwardUrl));
        }
    }
}