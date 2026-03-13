package base;

import core.DriverFactory;
import listeners.TestListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

@ExtendWith(TestListener.class)
public class BaseTest {

    public static WebDriver driver;

    @BeforeEach
    void setUp() {

        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        driver = DriverFactory.createDriver(browser, headless);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}