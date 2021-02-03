package com.fenixcommunity.centralspace.app.service.security.role;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fenixcommunity.centralspace.app.configuration.security.SecurityUserGroup;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.domain.model.permanent.role.Role;
import com.fenixcommunity.centralspace.domain.model.permanent.role.RoleGroup;
import com.fenixcommunity.centralspace.domain.repository.permanent.role.RoleGroupRepository;
import com.fenixcommunity.centralspace.domain.repository.permanent.role.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleCreatorService {
    public static final String ROLE_PREFIX = "ROLE_";

    private final RoleRepository roleRepository;
    private final RoleGroupRepository roleGroupRepository;

    public Optional<Role> findRole(final Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findRole(final String name) {
        return roleRepository.findByName(name);
    }

    public Optional<RoleGroup> findRoleGroup(final Long id) {
        return roleGroupRepository.findById(id);
    }

    public Optional<RoleGroup> findRoleGroup(final String name) {
        return roleGroupRepository.findByName(name);
    }

    public Role createRole(@NotBlank(message = "not blank") @Size(min = 5, message = "size min 5") final String name,
                           final String description) {
        if (!SecurityUserGroup.getAllAuthorities().contains(name)) {
            throw new IllegalArgumentException("new role not allowed");
        }
        return roleRepository.findByName(name).orElse(createNewRole(name, description));
    }

    public RoleGroup createRoleGroup(@NotBlank(message = "not blank") final String name,
                                     final String description,
                                     final Set<Role> roles) {
        if (!SecurityUserGroup.getAllSecurityUserGroupNames().contains(name)) {
            throw new IllegalArgumentException("new role group not allowed");
        }
        return roleGroupRepository.findByName(name).orElse(createNewRoleGroup(name, description, roles));

    }

    public void assignRoleToRoleGroup(final Long roleGroupId, final Role role) {
        final RoleGroup roleGroup = roleGroupRepository.findById(roleGroupId)
                .orElseThrow(() -> new ServiceFailedException("Assign role to role group failed"));
        final Set<Role> roles = roleGroup.getRoles();
        roles.add(role);
        roleGroup.setRoles(roles);
        roleGroupRepository.save(roleGroup);
    }

    private Role createNewRole(final String name,
                               final String description) {
        final String roleName = resolveRolePrefix(name);
        final Role roleToCreate = Role.builder()
                .name(roleName)
                .description(description)
                .build();
        return roleRepository.save(roleToCreate);
    }

    private RoleGroup createNewRoleGroup(final String name,
                                         final String description,
                                         final Set<Role> roles) {
        final RoleGroup roleGroupToCreate = RoleGroup.builder()
                .name(name)
                .description(description)
                .roles(roles)
                .build();
        return roleGroupRepository.save(roleGroupToCreate);
    }

    private String resolveRolePrefix(String name) {
        if (!name.startsWith(ROLE_PREFIX)) {
            return ROLE_PREFIX + name;
        }
        return name;
    }
}
