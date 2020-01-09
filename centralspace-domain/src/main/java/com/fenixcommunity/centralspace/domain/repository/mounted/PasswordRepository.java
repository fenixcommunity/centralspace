package com.fenixcommunity.centralspace.domain.repository.mounted;

import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
