package com.fenixcommunity.centralspace.utilities.rest;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class RestTool {

    public static String removeLinks(String content) {
        String result = content;
        if (isNotEmpty(content)) {
            result = result.replaceAll(",*\"_links\":\\{(.*?)}}", "");
            result =  result.replaceAll(",*\"links\":\\[(.*?)]", "");
        }
        return result;
    }
}
