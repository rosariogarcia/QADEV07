package com.fundacionjala.pivotal.framework.selenium;

import org.openqa.selenium.WebDriverException;

public final class FactoryDriver {

    private FactoryDriver() {
    }

    public static IDriver getDriver(String browser) {
        Browser currentBrowser = Browser.valueOf(browser.toUpperCase());
        if (Browser.CHROME.equals(currentBrowser)) {
            return new Chrome();
        } else if (Browser.FIREFOX.equals(currentBrowser)) {
            return new Firefox();
        } else if (Browser.REMOTE.equals(currentBrowser)) {
            return new RemoteBrowser();
        } else {
            throw new WebDriverException("Browser not found : " + browser);
        }
    }
}
