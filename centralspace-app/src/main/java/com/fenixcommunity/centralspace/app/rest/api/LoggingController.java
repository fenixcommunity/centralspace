package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;

import com.fenixcommunity.centralspace.app.configuration.annotation.MethodMonitoring;
import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerQueryDto;
import com.fenixcommunity.centralspace.app.rest.dto.logger.LoggerResponseDto;
import com.fenixcommunity.centralspace.app.rest.exception.ErrorDetails;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.app.service.appstatus.AppStatusService;
import com.fenixcommunity.centralspace.app.service.serviceconnector.RemoteService;
import com.fenixcommunity.centralspace.domain.core.redis.RedisService;
import com.fenixcommunity.centralspace.domain.model.memory.h2.SessionAppInfo;
import com.fenixcommunity.centralspace.domain.model.memory.redis.UserSession;
import com.fenixcommunity.centralspace.metrics.service.analyzer.AppMetadataLoader;
import com.fenixcommunity.centralspace.utilities.time.TimeTool;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RestController @RequestMapping("/api/logger")
@Log4j2
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class LoggingController {

    private final AppStatusService appStatusService;
    private final RedisService redisService;
    private final TimeTool timeTool;

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = ErrorDetails.class, message = "OK"),
            @ApiResponse(code = 400, response = ErrorDetails.class, message = "Bad request"),
            @ApiResponse(code = 404, response = ErrorDetails.class, message = "Not found"),
            @ApiResponse(code = 415, response = ErrorDetails.class, message = "Unsupported"),
            @ApiResponse(code = 500, response = ErrorDetails.class, message = "Internal server error"),
            @ApiResponse(code = 501, response = ErrorDetails.class, message = "Not implemented for given extraction type")
    })
    @GetMapping("/test")
    public ResponseEntity<ErrorDetails> runLogsAndThrowExceptionToHandler() {
        log.trace("TRACE Message");
        if (log.isDebugEnabled()) {
            log.debug("DEBUG Message");
        }
        log.info("INFO Message");
        log.warn("WARN Message");
        log.error("ERROR Message");

        final ErrorDetails errorDetails = ErrorDetails.builder()
                .message("error")
                .details("details").build();
        if (true) {
            final Exception exception = new HttpMediaTypeNotSupportedException(MediaType.APPLICATION_JSON, List.of(MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_STREAM_JSON));
            throw new ServiceFailedException("Exception handler test", exception);
        } else {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorDetails);
        }
    }

    //todo from elastic search
    //todo info about system controller and refactoring
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/basic-info")
    public Mono<LoggerResponseDto> getInfo(@RequestBody LoggerQueryDto loggerDto, @ApiIgnore HttpSession httpSession) {
        log.info(loggerDto.toString());

        // h2 memory records test
        final SessionAppInfo sessionAppInfo = appStatusService.createSessionAppInfo("warning");


        final JSONObject jsonObject = new JSONObject(Map.of("sessionAppInfoId", sessionAppInfo.getId()));
        jsonObject.put("sessionAppInfoSomeInfo", sessionAppInfo.getSomeInfo());

        final String sessionId = httpSession.getId();
        appStatusService.createUserSession(sessionId);
        final UserSession userSession = appStatusService.findUserSession(sessionId);
        final Set<String> redisUserSessionKeys = redisService.getRedisKeys("user_session");
        final Set<String> redisSessionKeys = redisService.getUsersSessionKeys();

        jsonObject.put("sessionId", sessionId);
        final long sessionCreationTime = httpSession.getCreationTime();
        jsonObject.put("sessionCreationTime",
                timeTool.fromMilliseconds(sessionCreationTime)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        final long lastAccessedTime = httpSession.getLastAccessedTime();
        jsonObject.put("sessionLastAccessedTime",
                timeTool.fromMilliseconds(lastAccessedTime)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        final int sessionTimeoutPeriod = httpSession.getMaxInactiveInterval();
        jsonObject.put("sessionTimeoutPeriod", sessionTimeoutPeriod);
        long now = timeTool.toMilliseconds(ZonedDateTime.now());
        final long sessionRemainingTime = ((sessionTimeoutPeriod * 1000) - (now - lastAccessedTime)) / 1000;
        jsonObject.put("sessionRemainingTime - seconds", sessionRemainingTime);
        jsonObject.put("now",
                timeTool.fromMilliseconds(now)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        final JSONArray jsonArray = new JSONArray(); // CDL.toJSONArray(stingJson)
        jsonArray.put(Boolean.TRUE);

        final AppMetadataLoader<RemoteService> appMetadataLoaderForRemoteService = new AppMetadataLoader<>();
        appMetadataLoaderForRemoteService.getMetadataInformation(RemoteService.class);

        final AppMetadataLoader<MethodMonitoring> appMetadataLoaderForMethodMonitoring = new AppMetadataLoader<>();
        appMetadataLoaderForMethodMonitoring.getAllGraphScanResultForAnnotation(MethodMonitoring.class);


        return Mono.just(LoggerResponseDto.builder()
                .log(jsonObject.toString())
                .details(jsonArray.toString())
                .loggerType(loggerDto.getLoggerType()).build());
    }
}
