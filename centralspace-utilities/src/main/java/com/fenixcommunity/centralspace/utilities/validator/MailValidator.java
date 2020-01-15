package com.fenixcommunity.centralspace.utilities.validator;

import com.fenixcommunity.centralspace.utilities.mail.MailBuilder;
import com.fenixcommunity.centralspace.utilities.mail.template.MailMessageTemplate;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.MailPreparationException;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MailValidator implements Validator {

    @Override
    public boolean isValid(final Object object) {
        if (object instanceof MailMessageTemplate) {
            final MailMessageTemplate mailTemplate = (MailMessageTemplate) object;
            return mailTemplate.isTemplateReady();
        }
        if (object instanceof MailBuilder) {
            final MailBuilder mail = (MailBuilder) object;
            return mail.isReadyToSend();
        }
        return false;
    }

    @Override
    public boolean isValidAll(final Object... obj) {
        return isValid(obj);
    }

    @Override
    public void validateWithException(final Object object) {
        if (!isValid(object)) {
            throw new MailPreparationException("Mail template hasn't filled");
        }
    }

    @Override
    public void validateAllWithException(final Object... obj) {
        validateWithException(obj);
    }

}
