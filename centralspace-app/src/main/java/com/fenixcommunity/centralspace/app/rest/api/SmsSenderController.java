package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.fenixcommunity.centralspace.app.service.sms.SmsSenderService;
import com.twilio.rest.api.v2010.account.Message;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController @RequestMapping("/api/sms-sender")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true) @AllArgsConstructor(access = PACKAGE)
public class SmsSenderController {

    private final SmsSenderService smsSenderService;

    @PostMapping("/send-sms")
    public Mono<Message> sendSms(@RequestParam(value = "deliverToPhoneNo", defaultValue = "+48517216172") final String deliverToPhoneNo,
                                 @RequestParam(value = "message", defaultValue = "message") final String message) {
        final Message sentMessage = smsSenderService.sendSMS(deliverToPhoneNo, message);

        return Mono.just(sentMessage);
    }

    @PostMapping("/send-mms")
    public Mono<Message> sendSms(@RequestParam(value = "deliverToPhoneNo", defaultValue = "+48517216172") final String deliverToPhoneNo,
                                 @RequestParam(value = "message", defaultValue = "message") final String message,
                                 @RequestParam(value = "imageURIPath", defaultValue = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png") final String imageURIPath) {
        final Message sentMessage = smsSenderService.sendMMS(deliverToPhoneNo, message, imageURIPath);

        return Mono.just(sentMessage);
    }

    @GetMapping("/sent-messages")
    public Flux<Message> getSentMessages() {
        return Flux.fromIterable(smsSenderService.getSentMessages());
    }

    @GetMapping("/check-delivery-status")
    public Mono<String> checkDeliveryStatus() {
        return Mono.just(smsSenderService.checkDeliveryStatus());
    }
}
