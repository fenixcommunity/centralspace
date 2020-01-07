package com.fenixcommunity.centralspace.domain.repository.mounted;

import com.fenixcommunity.centralspace.domain.model.mounted.password.Password;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepository extends CrudRepository<Password, Long> {
}
