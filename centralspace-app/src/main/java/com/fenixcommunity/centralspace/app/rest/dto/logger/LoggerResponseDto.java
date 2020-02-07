package com.fenixcommunity.centralspace.app.rest.dto.logger;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @Builder @FieldDefaults(level = PRIVATE)
public class LoggerResponseDto {

    @NotEmpty
    private String log;
    private String details;
    private String loggerType = "DEFAULT";

    @JsonCreator
    public LoggerResponseDto(@JsonProperty("log") String log,
                             @JsonProperty("details") String details,
                             @JsonProperty("loggerType") String loggerType) {
        this.log = log;
        this.details = details;
        if (isNotEmpty(loggerType)) {
            this.loggerType = loggerType;
        }
    }
}
