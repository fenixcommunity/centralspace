package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.websocket.server.PathParam;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.fenixcommunity.centralspace.metrics.service.MetricsService;
import com.fenixcommunity.centralspace.metrics.service.MetricsName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/metrics")
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class MetricsController {

    private MetricsService metricsService;

    @GetMapping("/{metricsName}")
    public String getMetrics(@PathParam("metricsName") MetricsName metricsName) {
        return "Total amount: " + metricsService.getSummary(metricsName).totalAmount();
    }

}
