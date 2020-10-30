package com.fenixcommunity.centralspace.utilities.configuration.properties;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotBlank;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "resource")
@PropertySource(value = "classpath:resource.yml", factory = YamlFetcher.class)
@Getter @Setter @FieldDefaults(level = PRIVATE)
public class ResourceProperties {

    @NotBlank
    private String imageUrl;
    @NotBlank
    private String convertedPdfPath;
    @NotBlank
    private String convertedHtmlPath;
    @NotBlank
    private String convertedImagePath;
    @NotBlank
    private String convertedTxtPath;
    @NotBlank
    private String convertedDocxPath;
    @NotBlank
    private String convertedCsvPath;
    @NotBlank
    private String convertedJsonPath;
}
