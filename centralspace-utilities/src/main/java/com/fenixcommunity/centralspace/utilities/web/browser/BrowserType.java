package com.fenixcommunity.centralspace.utilities.web.browser;

import static org.apache.logging.log4j.util.Strings.isBlank;

import lombok.Getter;

@Getter
public enum BrowserType {
    FIREFOX("firefox"),
    FIREFOX_PROXY("firefoxproxy"),
    FIREFOX_CHROME("firefoxchrome"),
    GOOGLECHROME("googlechrome"),
    SAFARI("safari"),
    OPERA("opera"),
    OPERA_BLINK("operablink"),
    EDGE("microsoftedge"),
    IEXPLORE("iexplore"),
    IEXPLORE_PROXY("iexploreproxy"),
    SAFARI_PROXY("safariproxy"),
    CHROME("chrome"),
    KONQUEROR("konqueror"),
    MOCK("mock"),
    IE_HTA("iehta"),
    ANDROID("android"),
    HTMLUNIT("htmlunit"),
    IE("internetexplorer"),
    IPHONE("iPhone"),
    IPAD("iPad"),
    PHANTOMJS("phantomjs"),
    UNKNOWN("unknown");

    String description;

    BrowserType(String description) {
        this.description = description;
    }

    //todo browserInfo searching
    public static BrowserType identifyBrowser(String browserInfo) {
        if (isBlank(browserInfo)) {
            return UNKNOWN;
        }
        switch (browserInfo.toLowerCase()) {
            case "firefox":
                return FIREFOX;
            case "chrome":
                return CHROME;
            default:
                return UNKNOWN;
        }
    }
}
