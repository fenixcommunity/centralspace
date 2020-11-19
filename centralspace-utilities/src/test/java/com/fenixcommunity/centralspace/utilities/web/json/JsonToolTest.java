package com.fenixcommunity.centralspace.utilities.web.json;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import com.fenixcommunity.centralspace.utilities.resourcehelper.FileUtils;
import com.fenixcommunity.centralspace.utilities.test.ReplaceCamelCaseAndUnderscore;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceCamelCaseAndUnderscore.class)
class JsonToolTest {

    @Nested
    class whenCompareJsonPair {
        @Test
        void givenTheSameJsonPair_thenResultTrue() {
            String objectsJson1 = FileUtils.loadFile("json/the_same_account_1.json");
            String objectsJson2 = FileUtils.loadFile("json/the_same_account_2.json");

            boolean result = JsonTool.compareJsonPair(objectsJson1, objectsJson2);
            assertThat(result).isTrue();
        }
    }

    @Test
    void getJsonRootItems() {
        String json = FileUtils.loadFile("json/the_same_account_1.json");
        Map<String, String> jsonRootItems = JsonTool.getJsonRootItems(json);
        assertThat(jsonRootItems).hasSize(2);
        JSONArray jsonObject1 = JsonTool.getJsonObject(json, "$.password.*");
        JSONArray jsonObject2 = JsonTool.getJsonObject(json, "$.account.skills[*]");
        assertThat(jsonObject1).hasSize(1);
        assertThat(jsonObject2).hasSize(3);

    }

}