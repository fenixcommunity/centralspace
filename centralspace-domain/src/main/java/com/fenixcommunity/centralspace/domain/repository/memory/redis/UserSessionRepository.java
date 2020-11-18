package com.fenixcommunity.centralspace.domain.repository.memory.redis;

import com.fenixcommunity.centralspace.domain.model.memory.redis.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
}