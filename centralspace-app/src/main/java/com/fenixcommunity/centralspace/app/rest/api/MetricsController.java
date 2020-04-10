package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;


import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.fenixcommunity.centralspace.metrics.service.MetricsService;
import com.fenixcommunity.centralspace.metrics.service.MetricsName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/metrics")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class MetricsController {

    private MetricsService metricsService;

    @GetMapping("/{metricsName}")
    public ResponseEntity<String> getMetrics(@PathVariable("metricsName") final MetricsName metricsName, @RequestParam(required=false) String statusCode) {
        return ResponseEntity.ok(metricsService.getRestSummary(metricsName, statusCode));
    }

}
