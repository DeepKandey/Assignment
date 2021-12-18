package com.testvagrant.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class BaseWebDriver {

    protected WebDriver driver;

    protected ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @BeforeMethod
    protected void createDriver(ITestContext testContext) throws Exception {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));

        String runType = properties.getProperty("runType");
        if ("LOCAL".equalsIgnoreCase(runType)) {
            createLocalDriver();
        }
    }

    protected void createLocalDriver() throws IOException{
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/maven.properties"));

        String browser = properties.getProperty("localBrowser");

      driver =  switch (browser.toUpperCase()) {
            case "CHROME" ->  {
                   WebDriverManager.chromedriver().setup();
                   ChromeOptions options = new ChromeOptions();
                   options.addArguments("--disable-notifications");
                   options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                    yield new ChromeDriver(options);
            }
            case "FIREFOX" -> {
                    WebDriverManager.firefoxdriver().setup();
                    yield new FirefoxDriver();
            }
          default -> {
              System.out.println("OK");
              yield null;
          }
        };
        driverThreadLocal.set(driver);
        getDriver().manage().window().maximize();
    }

    @AfterMethod
    protected void removeWebDriver() {
        if (driverThreadLocal != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
