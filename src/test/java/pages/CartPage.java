package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private By cartItems = By.cssSelector(".cart_item");
    private By removeButton = By.cssSelector("[data-test^='remove']");
    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By cartContainer = By.id("cart_contents_container");

    public int getCartItemCount() {

        waitVisible(cartContainer);

        return driver.findElements(cartItems).size();
    }

    public void removeItem() {

        waitVisible(cartContainer);

        int before = getCartItemCount();

        click(removeButton);

        wait.until(d ->
                d.findElements(cartItems).size() == Math.max(before - 1, 0)
        );
    }

    public void proceedToCheckout() {

        click(checkoutButton);

        wait.until(d -> driver.getCurrentUrl().contains("checkout-step-one"));

        wait.until(d -> driver.findElements(By.cssSelector("[data-test='firstName']")).size() > 0);
    }
}