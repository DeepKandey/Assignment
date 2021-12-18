package com.testvagrant.PageObjects;

import com.testvagrant.base.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccuWeatherHomePage extends BasePage {

  @FindBy(css = "input.search-input")
  private WebElement searchInput;

  @FindBy(xpath = "//div[@class='results-container']")
  private WebElement resultsContainer;

  public AccuWeatherHomePage(WebDriver driver) {
    super(driver);
  }

  public boolean isPageLoaded() {
    try {
      waitForPageToLoad();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public void enterLocation() {
    waitForElementToBeVisible(searchInput);
    searchInput.sendKeys("Pune");
    waitForElementToBeVisible(resultsContainer);
    searchInput.sendKeys(Keys.DOWN);
    searchInput.sendKeys(Keys.ENTER);
  }
}
