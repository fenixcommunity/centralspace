package com.fenixcommunity.centralspace.app.service.appstatus;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.domain.model.memory.SessionAppInfo;
import com.fenixcommunity.centralspace.domain.repository.memory.SessionAppInfoRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppStatusService {

    private final SessionAppInfoRepository sessionAppInfoRepository;

    public SessionAppInfo createSessionAppInfo(@NonNull final String someInfo) {
        final SessionAppInfo sessionAppInfo = SessionAppInfo.builder()
                .someInfo(someInfo)
                .build();
        return sessionAppInfoRepository.save(sessionAppInfo);
    }
}
