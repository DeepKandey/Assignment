package com.testvagrant.PageObjects;

import com.testvagrant.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WeatherForecastPage extends BasePage {

  @FindBy(xpath = "//span[@class='cur-con-weather-card__cta']//span[text()='More Details']")
  private WebElement moreDetailsTextElement;

  @FindBy(xpath = "//div[@class='temp']")
  private WebElement temperatureInCelsius;

  @FindBy(xpath = "((//div[@class='detail-item spaced-content'])[2]//div)[2]")
  private WebElement windSpeed;

  @FindBy(xpath = "((//div[@class='detail-item spaced-content'])[4]//div)[2]")
  private WebElement humidityValue;

  @FindBy(css = "div#dismiss-button")
  private WebElement dismissButton;

  public WeatherForecastPage(WebDriver driver) {
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

  public void clickMoreDetails() {
    waitForElementToBeVisible(moreDetailsTextElement);
    elementClick(moreDetailsTextElement);
  }

  public String getTemperature() {
    waitForElementToBeVisible(temperatureInCelsius);
    return temperatureInCelsius.getText().replaceAll("[^0-9\\.]", "").trim();
  }

  public String getWindSpeed() {
    waitForElementToBeVisible(windSpeed);
    return windSpeed.getText().replaceAll("[^0-9\\.]", "").trim();
  }

  public String getHumidity() {
    waitForElementToBeVisible(humidityValue);
    return humidityValue.getText().replaceAll("[^0-9\\.]", "").trim();
  }
}
