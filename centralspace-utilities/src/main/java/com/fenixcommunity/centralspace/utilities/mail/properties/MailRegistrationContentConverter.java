package com.fenixcommunity.centralspace.utilities.mail.properties;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MailRegistrationContentConverter implements Converter<String, MailRegistrationContent> {

    @Override
    public MailRegistrationContent convert(final String fullUrl) {
        MailRegistrationContent instance = null;
        if (isNotEmpty(fullUrl)) {
            String[] allData = fullUrl.split("\\?domain=");
            if (allData.length == 2) {
                String[] paramData = allData[1].split("&token=");
                if (paramData.length == 2) {
                    instance = new MailRegistrationContent(fullUrl, allData[0], paramData[0]);
                }
            }
        }
        return instance;
    }
}
