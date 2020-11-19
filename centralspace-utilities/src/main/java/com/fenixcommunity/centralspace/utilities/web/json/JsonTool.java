package com.fenixcommunity.centralspace.utilities.web.json;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.experimental.FieldDefaults;
import net.minidev.json.JSONArray;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class JsonTool {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final NodeComparatorHelper nodeComparatorHelper = new NodeComparatorHelper();

    public static boolean compareJsonPair(final String json1, final String json2) {
        try {
            return (mapper.readTree(json1))
                    .equals(nodeComparatorHelper, mapper.readTree(json2));
        } catch (JsonProcessingException e) {
            throw new JsonToolException("compareJsonPair failed");
        }
    }

    public static Map<String, String> getJsonRootItems(final String json) {
        return JsonPath.read(json, "$");
    }

    public static JSONArray getJsonObject(final String json, final String objectPath) {
        return JsonPath.read(json, objectPath);
    }
}
