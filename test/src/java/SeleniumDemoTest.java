import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class) public class SeleniumDemoTest {
  
  @Parameters public static Collection<String[]> browsers() {
    return Arrays.asList(new String[][] {{"firefox"},{"chrome"}});
  }
  
  public SeleniumDemoTest(String browser) {
    this.browser = browser;
  }

  private StringBuffer verificationErrors = new StringBuffer();

  private WebDriver driver;
  private String baseUrl = "https://www.amazon.com/";
  private String browser = "firefox";

  @Before public void setUp() throws Exception {
    if (browser.equals("firefox")) {
      System.setProperty("webdriver.gecko.driver",
          "ext\\geckodriver-v0.16.1-win64\\geckodriver.exe");
      DesiredCapabilities capabilities = DesiredCapabilities.firefox();
      capabilities.setCapability("marionette", true);
      capabilities.setJavascriptEnabled(true);
      driver = new FirefoxDriver(capabilities);
    } else if (browser.equals("chrome")) {
      System.setProperty("webdriver.chrome.driver",
          "ext\\chromedriver-v2.29-win32\\chromedriver.exe");
      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability("marionette", true);
      driver = new ChromeDriver(capabilities);
    }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test public void testThatDemonstratesSelenium() {
    driver.get(baseUrl + "/ref=nav_logo");
    driver.findElement(By.id("twotabsearchtextbox")).clear();
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys("box");
    new WebDriverWait(driver, 10)
    .until(ExpectedConditions.attributeContains(By.id("nav-flyout-searchAjax"), "style", "display: block;"));
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ARROW_DOWN);
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ARROW_DOWN);
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ARROW_DOWN);
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ARROW_DOWN);
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ARROW_DOWN);
    driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ENTER);
    new WebDriverWait(driver, 10)
    .until(ExpectedConditions.urlContains("field-keywords=boxing+gloves"));
    assertTrue(driver.getCurrentUrl().contains("field-keywords=boxing+gloves"));
  }

  @After public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
