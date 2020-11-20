package com.fenixcommunity.centralspace.utilities.test;


import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Value
@Log4j2
public class PrepareTestExtension implements BeforeAllCallback, BeforeEachCallback {
    private final String someInfo;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        log.info("SomeInfo {} In beforeAll : {}",
                someInfo, extensionContext.getDisplayName());
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        log.info("someInfo {} In beforeEach : {}",
                someInfo, extensionContext.getDisplayName());
    }
}
