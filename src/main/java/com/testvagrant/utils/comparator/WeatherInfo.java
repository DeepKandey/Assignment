package com.testvagrant.utils.comparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WeatherInfo {
  private String cityName;
  private float tempDegrees;
  private float windSpeed;
  private int humidity;
}
