package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final String DRIVER_PATH = System.getProperty("user.dir") + "/drivers/";

    public static WebDriver createDriver(String browser, boolean headless) {

        switch (browser) {

            case "chrome":
                System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver.exe");

                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");

                return new ChromeDriver(chromeOptions);

            case "firefox":
                System.setProperty("webdriver.gecko.driver", DRIVER_PATH + "geckodriver.exe");

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");

                return new FirefoxDriver(firefoxOptions);

            case "edge":
                System.setProperty("webdriver.edge.driver", DRIVER_PATH + "msedgedriver.exe");

                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");

                return new EdgeDriver(edgeOptions);

            default:
                throw new RuntimeException("Browser não suportado: " + browser);
        }
    }
}