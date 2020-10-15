package com.fenixcommunity.centralspace.domain.repository.memory;

import com.fenixcommunity.centralspace.domain.model.memory.SessionAppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionAppInfoRepository extends JpaRepository<SessionAppInfo, Long> {
}
