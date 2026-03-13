package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By cartItems = By.cssSelector(".cart_item");

    private By removeButton = By.cssSelector("[data-test^='remove']");

    private By checkoutButton = By.cssSelector("[data-test='checkout']");

    public int getCartItemCount() {

        return driver.findElements(cartItems).size();
    }

    public void removeItem() {

        wait.until(
                ExpectedConditions.elementToBeClickable(removeButton)
        ).click();
    }

    public void proceedToCheckout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(checkoutButton)
        ).click();
    }
}