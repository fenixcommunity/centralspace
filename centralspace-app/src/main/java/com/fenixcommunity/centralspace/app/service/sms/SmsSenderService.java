package com.fenixcommunity.centralspace.app.service.sms;

import static com.fenixcommunity.centralspace.utilities.common.Var.LINE;
import static lombok.AccessLevel.PRIVATE;

import java.net.URI;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import javax.validation.constraints.Size;

import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.utilities.async.AsyncFutureHelper;
import com.twilio.base.ResourceSet;
import com.twilio.converter.Promoter;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SmsSenderService {
    private final TwilioRestClient twilioRestClient;
    private final String smsProviderPhoneNo;

    @Autowired
    public SmsSenderService(final TwilioRestClient twilioRestClient,
                            @Value("${provider.twilio.phoneNo}") final String smsProviderPhoneNo) {
        if (twilioRestClient == null) {
            throw new ServiceFailedException("SmsProvider hasn't been configured");
        }
        this.twilioRestClient = twilioRestClient;
        this.smsProviderPhoneNo = smsProviderPhoneNo;
    }

    public Message sendSMS(final String deliverToPhoneNo, @Size(min = 2, max = 160) final String message) {
        return Message.creator(
                new PhoneNumber(deliverToPhoneNo),
                new PhoneNumber(smsProviderPhoneNo),
                message)
                .create();
    }

    public Message sendMMS(final String deliverToPhoneNo, @Size(min = 2, max = 160) final String message, String imageURIPath) {
        return Message.creator(
                new PhoneNumber(deliverToPhoneNo),
                new PhoneNumber(smsProviderPhoneNo),
                message)
                .setMediaUrl(Promoter.listOfOne(URI.create("http://www.domain.com/image.png")))
                .create();
    }

    public List<Message> getSentMessages() {
        return IteratorUtils.toList(Message.reader().read().iterator());
    }

    public String checkDeliveryStatus() {
        final StringJoiner joiner = new StringJoiner(LINE);
        final CompletableFuture<ResourceSet<Message>> future = Message.reader().readAsync();

        AsyncFutureHelper.get(future).forEach(message -> joiner.add(message.getSid() + " : " + message.getStatus()));

        return joiner.toString();
    }
}
