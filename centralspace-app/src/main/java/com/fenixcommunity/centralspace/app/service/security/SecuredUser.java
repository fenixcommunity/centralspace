package com.fenixcommunity.centralspace.app.service.security;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@Value @Builder @FieldDefaults(level = PRIVATE, makeFinal = true)
public class SecuredUser implements UserDetails {
    private static final long serialVersionUID = 2396654715019746670L;

    private final String id;
    private final String username;
    private final String mail;
    private final String password;
    private final Collection<GrantedAuthority> authorities;

    @JsonCreator
    SecuredUser(@JsonProperty("id") final String id,
                @JsonProperty("username") final String username,
                @JsonProperty("mail") final String mail,
                @JsonProperty("password") final String password,
                @JsonProperty("authorities") final Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.username = requireNonNull(username);
        this.mail = mail;
        this.password = requireNonNull(password);
        this.authorities = authorities;
    }

    @JsonIgnore
    public String getMail() {
        return mail;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //overridden lombok builder
    public static class SecuredUserBuilder {
        private String id;
        private String username;
        private String mail;
        private String password;
        private Collection<GrantedAuthority> authorities;

        public SecuredUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SecuredUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public SecuredUserBuilder mail(String mail) {
            this.mail = mail;
            return this;
        }

        public SecuredUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public SecuredUserBuilder authorities(List<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public SecuredUserBuilder roles(final List<String> roles) {
            final List<GrantedAuthority> authorities = new ArrayList<>(
                    roles.size());
            for (String role : roles) {
                Assert.isTrue(role.startsWith("ROLE_"), () -> role + " - role should start with ROLE_ ");
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return authorities(authorities);
        }

        public SecuredUser build() {
            return new SecuredUser(this.id, this.username, this.mail, this.password, this.authorities);
        }
    }
}