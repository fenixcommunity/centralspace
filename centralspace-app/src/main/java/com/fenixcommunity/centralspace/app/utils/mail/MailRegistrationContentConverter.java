package com.fenixcommunity.centralspace.app.utils.mail;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Component
@ConfigurationPropertiesBinding
public class MailRegistrationContentConverter implements Converter<String, MailRegistrationContent> {

    @Override
    public MailRegistrationContent convert(String fullUrl) {
        MailRegistrationContent instance = null;
        if (isNotEmpty(fullUrl)) {
            String[] allData = fullUrl.split("\\?domain=");
            if (allData.length == 2) {
                String[] paramData =  allData[1].split("&token=");
                if (paramData.length == 2) {
                    instance = new MailRegistrationContent(fullUrl, allData[0], paramData[0]);
                }
            }
        }
        return instance;
    }
}
