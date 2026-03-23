package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver() {

        String browser =
                System.getProperty("browser", "chrome");

        WebDriver driver;

        switch (browser.toLowerCase()) {

            case "edge":

                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "firefox":

                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:

                ChromeOptions options = new ChromeOptions();

                String chromeBinary =
                        System.getenv("CHROME_BINARY");

                if (chromeBinary != null &&
                        !chromeBinary.isBlank()) {

                    options.setBinary(chromeBinary);
                }

                options.addArguments("--start-maximized");
                options.addArguments("--disable-search-engine-choice-screen");
                options.addArguments("--remote-allow-origins=*");

                WebDriverManager.chromedriver().setup();

                driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        return driver;
    }
}