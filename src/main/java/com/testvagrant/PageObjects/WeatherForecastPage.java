package com.testvagrant.PageObjects;

import com.testvagrant.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WeatherForecastPage extends BasePage {

  @FindBy(xpath = "//div[@class='temp']")
  private WebElement temperatureInCelsius;

  @FindBy(xpath = "((//div[@class='spaced-content detail'])[1]/span)[2]")
  private WebElement realFeelShadeTemperature;

  @FindBy(xpath = "((//div[@class='spaced-content detail'])[2]/span)[2]")
  private WebElement airQuality;

  @FindBy(xpath = "((//div[@class='spaced-content detail'])[3]/span)[2]")
  private WebElement windSpeed;

  @FindBy(xpath = "((//div[@class='spaced-content detail'])[4]/span)[2]")
  private WebElement windGustSpeed;

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

  public String getTemperature() {
    waitForElementToBeVisible(temperatureInCelsius);
    return temperatureInCelsius.getText().trim();
  }

  public String getRealFeelShadeTemperature() {
    waitForElementToBeVisible(realFeelShadeTemperature);
    return realFeelShadeTemperature.getText().trim();
  }

  public String getAirQuality() {
    waitForElementToBeVisible(airQuality);
    return airQuality.getText().trim();
  }

  public String getWindSpeed() {
    waitForElementToBeVisible(windSpeed);
    return windSpeed.getText().trim();
  }

  public String getWindGustSpeed() {
    waitForElementToBeVisible(windGustSpeed);
    return windGustSpeed.getText().trim();
  }
}
