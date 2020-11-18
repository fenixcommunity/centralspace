package com.fenixcommunity.centralspace.domain.model.memory.redis;


import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;

import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("user_session")
@Value @FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserSession implements Serializable {

    @Id
    private final String sessionId;
}
