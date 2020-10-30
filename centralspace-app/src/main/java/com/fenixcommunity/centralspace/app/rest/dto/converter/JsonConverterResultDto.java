package com.fenixcommunity.centralspace.app.rest.dto.converter;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@JsonPropertyOrder({
        "converted",
        "resultOfConversion"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Data @FieldDefaults(level = PRIVATE)
public class JsonConverterResultDto {
    @JsonProperty("resultOfConversion")
    private String result;

    @JsonProperty("converted")
    private boolean converted;

    @JsonIgnore
    private BigDecimal score;
}
