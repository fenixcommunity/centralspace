package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PRIVATE;

import java.util.concurrent.atomic.AtomicLong;

import com.fenixcommunity.centralspace.app.rest.dto.utils.CrossOriginResponse;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/cross")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CrossOriginController {

    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/for9000")
    public CrossOriginResponse getCrossOriginFor9000(@RequestParam(required=false, defaultValue="localhost:9000") String calledFrom) {
        return new CrossOriginResponse(calledFrom, "Number of requests: " + counter.incrementAndGet());
    }

     /*
     TEST:
     curl -H "Origin: http://localhost:9000" --verbose \
     http://localhost:8088/app/api/cross/for9000?calledFrom=localhost:9000
     or
     centralspace-utilities/src/main/resources/js/jscaller.html
     */
}
