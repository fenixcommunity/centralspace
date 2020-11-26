package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import com.fenixcommunity.centralspace.app.rest.exception.ErrorDetails;
import com.fenixcommunity.centralspace.app.service.feature.AppFeatureChecker;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/features")
@Log4j2
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class AppFeatureController {

    private final AppFeatureChecker appFeatureChecker;

    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = ErrorDetails.class, message = "OK"),
    })
    @GetMapping("/list")
    public ResponseEntity<String> getListOfFeatures() {
        final Set<String> featureList = appFeatureChecker.getFeatureList();
        return ResponseEntity.ok(featureList.toString());
    }
}
