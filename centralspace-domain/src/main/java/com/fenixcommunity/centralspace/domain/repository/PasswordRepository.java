package com.fenixcommunity.centralspace.domain.repository;

import com.fenixcommunity.centralspace.domain.model.password.Password;

import org.springframework.data.repository.CrudRepository;

public interface PasswordRepository extends CrudRepository<Password, Long> {
}
