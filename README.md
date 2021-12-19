This test automation framework is based majorly on Selenium,Rest Assured and TestNG libraries. It provides the user to perform both Selenium driver UI tests and make API calls using Rest Assured.

We have following basic structure in the framework: -
BaseApi - This class handles the building of request specification to make API requests for various Http methods. Currently, it handles GET request but can be extended to handle other http methods like POST, PUT and DELETE.

BaseWebDriver - This class handles the creation and closing of WebDriver instance based on the browser on which UI tests need to be executed. Threadlocal has been used here to achieve parallel execution.

BasePage - This class handles common UI actions that we perform on any Web Page like click operation or waiting for the element to be visible.

Page Objects Package - POM design pattern has been followed in the framework. So, for every web page or components, I have created page objects classes to store locators and define methods to perform UI actions on those web elements.

Utils - Under this package, I have kept reusable or custom methods.

Configuration:
Maven properties file handles browser, base URI and site URL kind of things that are common for the entire framework. These values can be overridden when running tests using maven command using -D for parameterizing values
e.g: mvn clean test -DlocalBrowser=Chrome
data.properties to handle test specific data like search city name, variance allowed.

# maven command to execute the test - mvn clean test