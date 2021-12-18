package com.testvagrant.tests;

import com.google.gson.Gson;
import com.testvagrant.PageObjects.AccuWeatherHomePage;
import com.testvagrant.PageObjects.WeatherForecastPage;
import com.testvagrant.base.BaseApi;
import com.testvagrant.base.BaseWebDriver;
import com.testvagrant.utils.APIConstants;
import com.testvagrant.utils.comparator.WeatherInfo;
import com.testvagrant.utils.exception.NoSuchCityException;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class WeatherInfoTests extends BaseWebDriver {

  private AccuWeatherHomePage accuWeatherHomePage;
  private WeatherForecastPage weatherForecastPage;
  private WeatherInfo weatherInfoWeb, weatherInfoApi;
  private Gson gson;
  private Properties dataProperties;

  @BeforeMethod
  public void setUp() throws IOException {
    gson = new Gson();
    dataProperties = new Properties();
    dataProperties.load(this.getClass().getResourceAsStream("/testData/data.properties"));
  }

  @Test
  public void verifyCityWeatherInfo() throws NoSuchCityException {
    accuWeatherHomePage = new AccuWeatherHomePage(getDriver());
    weatherForecastPage = new WeatherForecastPage(getDriver());

    getWeatherInfoFromAPI(dataProperties.getProperty("cityName"));
    getWeatherInfoFromWeb(dataProperties.getProperty("cityName"));

    boolean compareTemp = tempComparator.compare(weatherInfoApi, weatherInfoWeb) == 0;
    boolean compareHumidity = humidityComparator.compare(weatherInfoApi, weatherInfoWeb) == 0;
    boolean compareWindSpeed = windSpeedComparator.compare(weatherInfoWeb, weatherInfoApi) == 0;

    if (compareTemp && compareHumidity && compareWindSpeed) assertTrue(true);
    else {
      String failureMessage = buildFailureMessage(compareTemp, compareHumidity, compareWindSpeed);
      fail(failureMessage);
    }
  }

  public void getWeatherInfoFromAPI(String cityName) {
    BaseApi baseApi = new BaseApi(APIConstants.baseURI, APIConstants.basePath_data);
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("appid", APIConstants.APIKey);
    queryParams.put("q", cityName);
    queryParams.put("units", "metric");
    baseApi.setQueryParams(queryParams);
    Response response = baseApi.getResponse(Method.GET, APIConstants.endPoint_weather);

    Map<String, Object> weatherResponseMap = new HashMap<>();

    weatherResponseMap.put("cityName", cityName);
    weatherResponseMap.put("tempDegrees", response.jsonPath().getDouble("main.temp"));
    weatherResponseMap.put("windSpeed", response.jsonPath().get("wind.speed"));
    weatherResponseMap.put("humidity", response.jsonPath().get("main.humidity"));

    weatherInfoApi =
        gson.fromJson(groovy.json.JsonOutput.toJson(weatherResponseMap), WeatherInfo.class);
  }

  public void getWeatherInfoFromWeb(String cityName) throws NoSuchCityException {
    accuWeatherHomePage.isPageLoaded();
    accuWeatherHomePage.enterLocation();
    weatherForecastPage.isPageLoaded();
    weatherForecastPage.clickMoreDetails();

    Map<String, Object> weatherInfoMapWeb = new HashMap<>();

    weatherInfoMapWeb.put("cityName", cityName);
    weatherInfoMapWeb.put("tempDegrees", weatherForecastPage.getTemperature());
    weatherInfoMapWeb.put("windSpeed", weatherForecastPage.getWindSpeed());
    weatherInfoMapWeb.put("humidity", weatherForecastPage.getHumidity());

    weatherInfoWeb =
        gson.fromJson(groovy.json.JsonOutput.toJson(weatherInfoMapWeb), WeatherInfo.class);
  }

  Comparator<WeatherInfo> tempComparator =
      ((o1, o2) -> {
        float absValue = Math.abs(o1.getTempDegrees() - o2.getTempDegrees());
        float tempVariance = Float.parseFloat(dataProperties.getProperty("tempVariance"));
        return (absValue >= 0 && absValue <= tempVariance) ? 0 : 1;
      });

  Comparator<WeatherInfo> humidityComparator =
      ((o1, o2) -> {
        float absValue = Math.abs(o1.getHumidity() - o2.getHumidity());
        float humidityVariance = Float.parseFloat(dataProperties.getProperty("humidityVariance"));
        return (absValue >= 0 && absValue <= humidityVariance) ? 0 : 1;
      });

  Comparator<WeatherInfo> windSpeedComparator =
      ((o1, o2) -> {
        float absValue = Math.abs(o1.getWindSpeed() - o2.getWindSpeed());
        float windSpeedVariance = Float.parseFloat(dataProperties.getProperty("windVariance"));
        return (absValue >= 0 && absValue <= windSpeedVariance) ? 0 : 1;
      });

  private String buildFailureMessage(
      boolean compareTemp, boolean compareHumidity, boolean compareWindSpeed) {
    StringBuilder failureMessage = new StringBuilder();
    failureMessage.append("One or more comparators (");
    if (!compareTemp) failureMessage.append("compareTemp");
    if (!compareHumidity) failureMessage.append("compareHumidity");
    if (!compareWindSpeed) failureMessage.append("compareWindSpeed");
    failureMessage.append("), returned a value outside the acceptable range(s)");
    return String.valueOf(failureMessage);
  }
}
