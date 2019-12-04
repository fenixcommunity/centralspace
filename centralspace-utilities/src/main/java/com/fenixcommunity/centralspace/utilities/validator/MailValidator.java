package com.fenixcommunity.centralspace.utilities.validator;

import com.fenixcommunity.centralspace.utilities.mail.MailBuilder;
import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import org.springframework.mail.MailPreparationException;

public class MailValidator implements Validator {

    @Override
    public boolean isValid(Object object) {
        if (object instanceof MailMessageTemplate) {
            MailMessageTemplate mailTemplate = (MailMessageTemplate) object;
            return mailTemplate.isTemplateReady();
        }
        if (object instanceof MailBuilder) {
            MailBuilder mail = (MailBuilder) object;
            return mail.isReadyToSend();
        }
        return false;
    }

    @Override
    public boolean isValidAll(Object... obj) {
        return isValid(obj);
    }

    @Override
    public void validateWithException(Object object) {
        if (!isValid(object)) {
            throw new MailPreparationException("Mail template hasn't filled");
        }
    }

    @Override
    public void validateAllWithException(Object... obj) {
        validateWithException(obj);
    }

}
