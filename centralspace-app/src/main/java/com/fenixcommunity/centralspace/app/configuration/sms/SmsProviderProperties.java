package com.fenixcommunity.centralspace.app.configuration.sms;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "provider.twilio")
@Getter @FieldDefaults(level = PRIVATE)
class SmsProviderProperties {
    private String sid;
    private String oAuthToken;
    private String phoneNo;

    @ConstructorBinding
    public SmsProviderProperties(String sid, String oAuthToken, String phoneNo) {
        this.sid = sid;
        this.oAuthToken = oAuthToken;
        this.phoneNo = phoneNo;
    }
}