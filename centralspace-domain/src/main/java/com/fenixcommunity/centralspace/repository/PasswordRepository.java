package com.fenixcommunity.centralspace.repository;

import com.fenixcommunity.centralspace.model.password.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
