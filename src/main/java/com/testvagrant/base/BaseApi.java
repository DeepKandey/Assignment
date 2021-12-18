package com.testvagrant.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Method;
import java.util.Map;

public class BaseApi {
  private RequestSpecification requestSpecification;
  private final RequestSpecBuilder requestSpecBuilder;

  public BaseApi(String baseURI, String basePath) {
    requestSpecBuilder = new RequestSpecBuilder().setBaseUri(baseURI).setBasePath(basePath);
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  public RequestSpecification buildRequestSpec() {
    requestSpecification = requestSpecBuilder.build().given();
    return requestSpecification;
  }

  public BaseApi setQueryParams(final Map<String,?> params){
    requestSpecBuilder.addQueryParams(params);
    return this;
  }

  public Response getResponse(final Method method, final String endPoint){
    if(requestSpecification ==null && requestSpecBuilder !=null){
      requestSpecification= buildRequestSpec();
    }

    return switch (method.toString()){
      case "GET" -> RestAssured.given().spec(requestSpecification).get();
    };
  }
}
