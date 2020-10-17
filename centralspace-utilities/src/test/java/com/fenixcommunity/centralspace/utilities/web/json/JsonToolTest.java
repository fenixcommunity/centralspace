package com.fenixcommunity.centralspace.utilities.web.json;


import static org.assertj.core.api.Assertions.assertThat;

import com.fenixcommunity.centralspace.utilities.resourcehelper.FileUtils;
import com.fenixcommunity.centralspace.utilities.test.ReplaceCamelCaseAndUnderscore;
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

}