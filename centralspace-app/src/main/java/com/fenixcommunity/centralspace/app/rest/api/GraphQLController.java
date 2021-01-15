package com.fenixcommunity.centralspace.app.rest.api;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;

import com.fenixcommunity.centralspace.app.service.graphql.AppGraphQLService;
import graphql.ExecutionResult;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/graphql")
@AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class GraphQLController {
    public static final String QUERY_PARAMETER = "query";
    public static final String OPERATION_NAME_PARAMETER = "operationName";
    public static final String VARIABLES_PARAMETER = "variables";
    private final AppGraphQLService appGraphQLService;

    @PostMapping(value = "/query", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExecutionResult graphql(@RequestBody final Map<String, Object> request) {
        return appGraphQLService.executeQuery(
                (String) request.get(QUERY_PARAMETER),
                (String) request.get(OPERATION_NAME_PARAMETER),
                (Map) request.get(VARIABLES_PARAMETER));
    }
}