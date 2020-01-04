package com.fenixcommunity.centralspace.app.service.security.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class SecuredUser implements UserDetails {
    private static final long serialVersionUID = 2396654715019746670L;

    String id;
    String username;
    String password;
    Collection<GrantedAuthority> authorities = new ArrayList<>();

    @JsonCreator
    SecuredUser(@JsonProperty("id") final String id,
                @JsonProperty("username") final String username,
                @JsonProperty("password") final String password,
                @JsonProperty("role") final String role) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
        this.authorities.add(new SimpleGrantedAuthority(role));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
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

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    //overrided lombock builder
    public static class SecuredUserBuilder {
        String id;
        String username;
        String password;
        String role;

        public SecuredUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SecuredUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public SecuredUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public SecuredUserBuilder role(String role) {
            this.role = role;
            return this;
        }

        public SecuredUser build() {
            return new SecuredUser(this.id, this.username, this.password, this.role);
        }
    }
}