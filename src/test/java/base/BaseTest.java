package base;

import core.DriverFactory;
import listeners.TestListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

@ExtendWith(TestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void setup() {

        driver = DriverFactory.createDriver();

        driver.get("https://www.saucedemo.com");

        driver.manage().deleteAllCookies();

        ((JavascriptExecutor) driver)
                .executeScript("window.localStorage.clear();");

        ((JavascriptExecutor) driver)
                .executeScript("window.sessionStorage.clear();");
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}