package com.fenixcommunity.centralspace.app.rest.dto.logger;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ApiModel
@Data @FieldDefaults(level = PRIVATE)
public class LoggerDto {

    private String loggerType = "DEFAULT";
    @NotEmpty
    private String log;

    @JsonCreator
    public LoggerDto(String loggerType, String log) {
        if (isNotEmpty(loggerType)) {
            this.loggerType = loggerType;
        }
        this.log = log;
    }
}
