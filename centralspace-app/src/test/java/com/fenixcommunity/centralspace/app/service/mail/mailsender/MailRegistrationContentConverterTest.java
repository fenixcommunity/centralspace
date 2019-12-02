package com.fenixcommunity.centralspace.app.service.mail.mailsender;

import com.fenixcommunity.centralspace.app.utils.mail.MailRegistrationContent;
import com.fenixcommunity.centralspace.app.utils.mail.MailRegistrationContentConverter;
import org.junit.jupiter.api.Test;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOMAIN_ID;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOMAIN_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MailRegistrationContentConverterTest {

    @Test
    void shouldConvert() {
        //given
        String url = DOMAIN_URL + "?domain=" + DOMAIN_ID + "&token=account_token";
        MailRegistrationContentConverter converter = new MailRegistrationContentConverter();
        //when
        MailRegistrationContent result = converter.convert(url);
        MailRegistrationContent expected = new MailRegistrationContent(url, DOMAIN_URL, DOMAIN_ID);
        //then
        assertNotNull(result);
        assertEquals(expected.getUrl(), result.getUrl());
        assertEquals(expected.getDomainId(), result.getDomainId());
    }
}