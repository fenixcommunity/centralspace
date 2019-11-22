package com.fenixcommunity.centralspace.utilities.rest;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class RestTool {

    public static String removeLinks(String content) {
        if (isNotEmpty(content)) {
            return content.replaceAll(",*\"links\":\\[(.*?)]", "");
        }
        return content;
    }
}
