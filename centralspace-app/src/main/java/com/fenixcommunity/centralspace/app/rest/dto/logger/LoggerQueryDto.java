package com.fenixcommunity.centralspace.app.rest.dto.logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
@Data @Builder @FieldDefaults(level = PRIVATE)
public class LoggerQueryDto {

    @NotEmpty
    private String log;
    private String loggerType = "DEFAULT";

    @JsonCreator
    public LoggerQueryDto(@JsonProperty("log") String log, @JsonProperty("loggerType") String loggerType) {
        this.log = log;
        if (isNotEmpty(loggerType)) {
            this.loggerType = loggerType;
        }
    }
}
