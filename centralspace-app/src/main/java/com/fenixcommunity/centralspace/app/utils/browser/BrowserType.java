package com.fenixcommunity.centralspace.app.utils.browser;

public enum BrowserType {
    FIREFOX("firefox"), FIREFOX_PROXY("firefoxproxy"), FIREFOX_CHROME("firefoxchrome"), GOOGLECHROME("googlechrome"), SAFARI("safari"), OPERA("opera"), OPERA_BLINK("operablink"),
    EDGE("MicrosoftEdge"), IEXPLORE("iexplore"), IEXPLORE_PROXY("iexploreproxy"), SAFARI_PROXY("safariproxy"), CHROME("chrome"), KONQUEROR("konqueror"),
    MOCK("mock"), IE_HTA("iehta"), ANDROID("android"), HTMLUNIT("htmlunit"), IE("internet explorer"), IPHONE("iPhone"), IPAD("iPad"), PHANTOMJS("phantomjs"),
    UNKNOWN("unknown");

    String description;

    BrowserType(String description) {
        this.description = description;
    }
//todo browserInfo searching
    public static BrowserType identifyBrowser(String browserInfo) {
        switch (browserInfo) {
            case "TODO":
                return FIREFOX;
            default:
                return UNKNOWN;
        }
    }
}
