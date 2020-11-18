package com.fenixcommunity.centralspace.app.service.appstatus;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.domain.model.memory.h2.SessionAppInfo;
import com.fenixcommunity.centralspace.domain.model.memory.redis.UserSession;
import com.fenixcommunity.centralspace.domain.repository.memory.h2.SessionAppInfoRepository;
import com.fenixcommunity.centralspace.domain.repository.memory.redis.UserSessionRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppStatusService {

    private final SessionAppInfoRepository sessionAppInfoRepository;
    private final UserSessionRepository userSessionRepository;

    public SessionAppInfo createSessionAppInfo(@NonNull final String someInfo) {
        final SessionAppInfo sessionAppInfo = SessionAppInfo.builder()
                .someInfo(someInfo)
                .build();
        return sessionAppInfoRepository.save(sessionAppInfo);
    }

    public UserSession createUserSession(@NonNull final String sessionId) {
        final UserSession userSession = new UserSession(sessionId);
        return userSessionRepository.save(userSession);
    }

    public UserSession findUserSession(@NonNull final String sessionId) {
        return userSessionRepository.findById(sessionId).orElse(null);
    }
}
