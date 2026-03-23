package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String user, String pass) {

        type(username, user);
        type(password, pass);
        click(loginBtn);

        wait.until(d -> driver.getCurrentUrl().contains("inventory"));

        wait.until(d -> driver.findElements(By.id("inventory_container")).size() > 0);

        wait.until(d -> driver.findElements(By.cssSelector(".inventory_item")).size() >= 6);

        return new InventoryPage(driver);
    }
}