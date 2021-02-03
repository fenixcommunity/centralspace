package com.fenixcommunity.centralspace.app.rest.api;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

import java.util.Set;
import java.util.stream.Collectors;

import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.BasicResponse;
import com.fenixcommunity.centralspace.app.rest.dto.security.role.AssignRoleGroupToAccountRestRequest;
import com.fenixcommunity.centralspace.app.rest.dto.security.role.AssignRoleToRoleGroupRestRequest;
import com.fenixcommunity.centralspace.app.rest.dto.security.role.RestRole;
import com.fenixcommunity.centralspace.app.rest.dto.security.role.RestRoleGroup;
import com.fenixcommunity.centralspace.app.rest.dto.security.role.RoleRestRequest;
import com.fenixcommunity.centralspace.app.service.account.AccountService;
import com.fenixcommunity.centralspace.app.service.security.role.RoleCreatorService;
import com.fenixcommunity.centralspace.domain.model.permanent.role.Role;
import com.fenixcommunity.centralspace.domain.model.permanent.role.RoleGroup;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api/role-creator")
@PreAuthorize("hasAuthority('ROLE_MANAGE_ROLE')")
@Validated
@Log4j2
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleCreatorController {
    private final RoleCreatorService roleCreatorService;
    private final AccountService accountService;

    @PostMapping(value = "/create-role-group", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createRoleGroup(@RequestBody final RoleRestRequest roleRestRequest) {
        final Set<Role> roles = roleRestRequest.roles.stream()
                .map(restRole -> roleCreatorService.createRole(restRole.name, restRole.description))
                .collect(toSet());
        final RestRoleGroup restRoleGroup = roleRestRequest.roleGroup;
        roleCreatorService.createRoleGroup(restRoleGroup.name, restRoleGroup.description, roles);
        return ResponseEntity.ok(BasicResponse.builder().status("DONE"));
    }

    @PostMapping(value = "/assing-new-role-to-role-group", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> assignNewRoleToRoleGroup(@RequestBody final AssignRoleToRoleGroupRestRequest assignRoleToRoleGroupRestRequest) {
        final RestRole restRole = assignRoleToRoleGroupRestRequest.restRole;
        final Role role = roleCreatorService.createRole(restRole.name, restRole.description);
        roleCreatorService.assignRoleToRoleGroup(assignRoleToRoleGroupRestRequest.roleGroupId, role);
        return ResponseEntity.ok(BasicResponse.builder().status("DONE"));
    }

    @PostMapping(value = "/assign-role-group-to-account", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> assignRoleGroupsToAccount(@RequestBody final AssignRoleGroupToAccountRestRequest assignRoleGroupToAccountRestRequest) {
        accountService.assignRoleToAccount(assignRoleGroupToAccountRestRequest.accountId, assignRoleGroupToAccountRestRequest.roleGroupsIds);
        return ResponseEntity.ok(BasicResponse.builder().status("DONE"));
    }
}
