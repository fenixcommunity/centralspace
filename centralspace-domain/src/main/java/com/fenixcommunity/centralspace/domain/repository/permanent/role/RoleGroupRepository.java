package com.fenixcommunity.centralspace.domain.repository.permanent.role;

import java.util.Optional;

import com.fenixcommunity.centralspace.domain.model.permanent.role.RoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {
    Optional<RoleGroup> findByName(String name);
}
