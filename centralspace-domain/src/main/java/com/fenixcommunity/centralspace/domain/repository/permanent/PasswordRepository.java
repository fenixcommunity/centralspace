package com.fenixcommunity.centralspace.domain.repository.permanent;

import com.fenixcommunity.centralspace.domain.model.permanent.password.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
