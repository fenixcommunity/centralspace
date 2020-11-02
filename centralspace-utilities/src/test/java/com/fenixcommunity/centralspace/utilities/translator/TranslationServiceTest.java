package com.fenixcommunity.centralspace.utilities.translator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import com.fenixcommunity.centralspace.utilities.time.TimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @InjectMocks
    private TranslationService translationService;

    @Test
    void getMessageTest() {
        String messagePL = translationService.getMessage(AppLabel.SENT_MASSAGE, AppLocate.PL);
        assertThat(messagePL).isEqualTo("Aplikacja wysłała ci wiadomość.");

        String messageUS = translationService.getMessage(AppLabel.SENT_MASSAGE, AppLocate.US);
        assertThat(messageUS).isEqualTo("Application has sent you a message.");
    }

    @Test
    void getExtendedMessageTest() {
        Date oldDate = Date.from(TimeFormatter.toIsoZonedDateTime("2011-12-12T10:15:30+01:00[Europe/Paris]").toInstant());
        String messageExtendedUS = translationService.getMessage(AppLabel.SENT_MESSAGE_WITH_AUTHOR_AND_DATE, AppLocate.US,
                0.6, oldDate, "Max", 2);
        assertThat(messageExtendedUS).isEqualTo("For 60% On Dec 12, 2011 - Max sent you two messages.");

        String messageExtendedUSNoMessages = translationService.getMessage(AppLabel.SENT_MESSAGE_WITH_AUTHOR_AND_DATE, AppLocate.US,
                0.6, oldDate, "Max", 0);
        assertThat(messageExtendedUSNoMessages).isEqualTo("For 60% On Dec 12, 2011 - Max sent you no messages.");

        String messageExtendedPL = translationService.getMessage(AppLabel.SENT_MESSAGE_WITH_AUTHOR_AND_DATE, AppLocate.PL,
                0.6, oldDate, "Max", 1);
        assertThat(messageExtendedPL).isEqualTo("Na 60% w dniu 12 gru 2011 - Max wysłał Ci jedną wiadomość.");

        String messageExtendedPLMoreThan2Message = translationService.getMessage(AppLabel.SENT_MESSAGE_WITH_AUTHOR_AND_DATE, AppLocate.PL,
                0.6, oldDate, "Max", 3);
        assertThat(messageExtendedPLMoreThan2Message).isEqualTo("Na 60% w dniu 12 gru 2011 - Max wysłał Ci 3 wiadomości.");
    }
}