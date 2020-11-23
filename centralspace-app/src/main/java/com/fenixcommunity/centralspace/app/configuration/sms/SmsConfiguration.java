package com.fenixcommunity.centralspace.app.configuration.sms;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import com.fenixcommunity.centralspace.utilities.common.YamlFetcher;
import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:sms-provider.yml", factory = YamlFetcher.class)
@EnableConfigurationProperties(SmsProviderProperties.class)
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class SmsConfiguration {

    @Bean
    public TwilioRestClient smsProvider(@NonNull SmsProviderProperties smsProviderProperties) {
        Twilio.init(smsProviderProperties.getSid(), smsProviderProperties.getOAuthToken()); // -> Twilio.setRegion();
        return Twilio.getRestClient();
    }
}
