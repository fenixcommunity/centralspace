package com.fenixcommunity.centralspace.app.service.security.user;

import static com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup.BASIC_USER;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.validation.Valid;

import com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup;
import com.fenixcommunity.centralspace.app.service.account.AccountService;
import com.fenixcommunity.centralspace.app.service.account.AddressService;
import com.fenixcommunity.centralspace.app.service.password.PasswordService;
import com.fenixcommunity.centralspace.app.service.security.role.RoleCreatorService;
import com.fenixcommunity.centralspace.app.service.security.SecuredUser;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Account;
import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import com.fenixcommunity.centralspace.domain.model.permanent.role.Role;
import com.fenixcommunity.centralspace.domain.model.permanent.role.RoleGroup;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SecuredUserCreator {
    private final AccountService accountService;
    private final PasswordService passwordService;
    private final RoleCreatorService roleCreatorService;
    private final AddressService addressService;
    private final JdbcUserDetailsManager userDetailsManager;

    @Transactional
    public void createSecuredUser(@Valid final CreateUserData createUserData) {
        if (!passwordService.passwordIsValid(createUserData.getPassword())
            || userDetailsManager.userExists(createUserData.getUsername())) {
            return;
        }
        sprawdz

        final Address address = addressService.createAddress(createUserData.getCountry());
        final RoleGroup roleGroup = getRoleGroup(createUserData, createUserData.getSecurityUserGroup());
        final Account account = accountService.createAccount(createUserData.getUsername(), createUserData.getMail(), address, roleGroup);
        final Password password = passwordService.generatePassword(createUserData.getPassword(), account);

        final SecuredUser securedUser = SecuredUser.builder()
                .username(account.getLogin())
                .mail(account.getMail())
                .password(password.getPassword())
                .roles(roleGroup.getRoles().stream().map(Role::getName).collect(toList()))
                .build();
sprawdz
        final Authentication authentication = new UsernamePasswordAuthenticationToken(securedUser, null, securedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private RoleGroup getRoleGroup(final CreateUserData createUserData, final SecurityUserGroup securityUserGroup) {
        if (securityUserGroup != null) {
            return roleCreatorService.findRoleGroup(securityUserGroup.name()).orElse(findOrCreateBasicRoleGroup());
        } else if (createUserData.getRoleGroupId() != null) {
            return roleCreatorService.findRoleGroup(createUserData.getRoleGroupId()).orElse(findOrCreateBasicRoleGroup());
        } else {
            return findOrCreateBasicRoleGroup();
        }
    }

    private RoleGroup findOrCreateBasicRoleGroup() {
        return roleCreatorService.findRoleGroup(BASIC_USER.name())
                .orElse(roleCreatorService.createRoleGroup(
                        BASIC_USER.name(),
                        BASIC_USER.getDescription(),
                        BASIC_USER.getAuthorities().stream().map(role -> roleCreatorService.createRole(role, role)).collect(toSet())
                ));
    }
}