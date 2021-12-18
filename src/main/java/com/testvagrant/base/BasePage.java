package com.testvagrant.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
  private static final int POLLING = 500;
  protected WebDriver driver;
  private int shortTimeout = 10;
  private int longTimeout = 60;
  private WebDriverWait shortWait;
  private WebDriverWait longWait;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    shortWait =
        new WebDriverWait(driver, Duration.ofSeconds(shortTimeout), Duration.ofMillis(POLLING));
    longWait =
        new WebDriverWait(driver, Duration.ofSeconds(longTimeout), Duration.ofMillis(POLLING));
    PageFactory.initElements(new AjaxElementLocatorFactory(driver, shortTimeout), this);
  }

  /**
   * {summary: Method to click on WebElement}
   *
   * @param element click on element
   * @author deepak
   */
  public void elementClick(WebElement element) {
    try {
      Actions actions = new Actions(this.driver);
      actions.moveToElement(element);
    } catch (Exception ignored) {
    }
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    } catch (Exception ignored) {
    }

    boolean clickSuccessful = false;
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
      clickSuccessful = true;
    } catch (Exception ignored) {
    }
    try {
      if (!clickSuccessful) {
        element.click();
      }
    } catch (Exception ignored) {
    }
  }

  protected void waitForElementToBeVisible(WebElement element) {
    WebDriverWait wait =
        new WebDriverWait(driver, Duration.ofSeconds(longTimeout), Duration.ofMillis(POLLING));
    wait.ignoring(NoSuchElementException.class);
    wait.ignoring(ElementClickInterceptedException.class);
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  /**
   * {summary Method to wait for page to load}
   *
   * @author deepak
   */
  public void waitForPageToLoad() {
    longWait.until(
        driver ->
            ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .toString()
                .equals("complete"));
  }
}
