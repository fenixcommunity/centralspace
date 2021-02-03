package com.fenixcommunity.centralspace.domain.repository.permanent.role;

import java.util.Optional;

import com.fenixcommunity.centralspace.domain.model.permanent.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
