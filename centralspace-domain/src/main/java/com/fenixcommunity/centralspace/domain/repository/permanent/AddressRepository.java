package com.fenixcommunity.centralspace.domain.repository.permanent;

import com.fenixcommunity.centralspace.domain.model.permanent.account.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}