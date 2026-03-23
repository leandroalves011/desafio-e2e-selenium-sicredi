package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.SortOption;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    private By inventoryContainer = By.id("inventory_container");

    private By productNames = By.cssSelector("[data-test='inventory-item-name']");
    private By productImages = By.cssSelector(".inventory_item_img img");

    private By addBackpack = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private By addBikeLight = By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']");
    private By addBoltTShirt = By.cssSelector("[data-test='add-to-cart-sauce-labs-bolt-t-shirt']");
    private By addFleeceJacket = By.cssSelector("[data-test='add-to-cart-sauce-labs-fleece-jacket']");
    private By addOnesie = By.cssSelector("[data-test='add-to-cart-sauce-labs-onesie']");
    private By addRedTshirt = By.cssSelector("[data-test='add-to-cart-test.allthethings()-t-shirt-(red)']");

    private By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");
    private By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");

    private By sortDropdown = By.cssSelector("[data-test='product-sort-container']");

    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");
    private By aboutLink = By.id("about_sidebar_link");

    private By itemPageTitle = By.cssSelector("[data-test='inventory-item-name']");
    private By itemPageAddButton = By.cssSelector("[data-test^='add-to-cart']");

    public boolean isInventoryDisplayed() {

        waitUrlContains("inventory");
        waitVisible(inventoryContainer);

        wait.until(d -> driver.findElements(productNames).size() >= 6);

        return true;
    }

    public boolean allImagesAreEqual() {

        List<WebElement> images = findAll(productImages);

        if (images.isEmpty()) return false;

        String firstSrc = images.get(0).getAttribute("src");

        for (WebElement img : images) {
            if (!img.getAttribute("src").equals(firstSrc)) {
                return false;
            }
        }

        return true;
    }

    public void addBackpackToCart() { click(addBackpack); }
    public void addBikeLightToCart() { click(addBikeLight); }
    public void addBoltTShirtToCart() { click(addBoltTShirt); }
    public void addFleeceJacketToCart() { click(addFleeceJacket); }
    public void addOnesieToCart() { click(addOnesie); }
    public void addRedTshirtToCart() { click(addRedTshirt); }

    public void removeFirstProductFromCart() {
        click(By.cssSelector("[data-test^='remove']"));
    }

    public int getCartItemCount() {
        return readCartBadge(cartBadge);
    }

    public List<String> getProductNames() {
        return findAll(productNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void sortProducts(SortOption option) {

        WebElement dropdownEl = waitVisible(sortDropdown);

        Select dropdown = new Select(dropdownEl);

        List<String> before = getProductNames();

        dropdown.selectByVisibleText(option.getValue());

        wait.until(d -> {
            List<String> after = getProductNames();
            return !after.equals(before);
        });
    }

    public void logout() {

        click(menuButton);
        waitVisible(logoutLink);
        click(logoutLink);

        waitVisible(By.id("login-button"));
    }

    public void clickAbout() {

        click(menuButton);
        waitVisible(aboutLink);
        click(aboutLink);

        wait.until(d -> driver.getCurrentUrl().contains("saucelabs"));
    }

    public boolean isOnSauceLabsPage() {
        return driver.getCurrentUrl().contains("saucelabs");
    }

    public void openItemByIndex(int index) {

        waitInventoryReload();

        wait.until(d -> driver.findElements(productNames).size() == 6);

        By itemLocator = By.cssSelector("[data-test='inventory-item-name']");

        wait.until(d -> {
            List<WebElement> items = driver.findElements(itemLocator);

            if (items.size() <= index) return false;

            items.get(index).click();

            return true;
        });

        wait.until(d ->
                driver.getCurrentUrl().contains("inventory-item")
                        || driver.findElements(itemPageTitle).size() > 0
        );
    }

    public void addItemFromItemPage() {
        click(itemPageAddButton);
    }

    public String getProductNameByIndex(int index) {
        return findAll(productNames).get(index).getText();
    }

    public String getItemPageTitle() {
        return waitVisible(itemPageTitle).getText();
    }

    public void openItemByName(String name) {

        waitInventoryReload();

        wait.until(d -> {

            List<WebElement> items =
                    driver.findElements(productNames);

            for (WebElement el : items) {

                if (el.getText().equals(name)) {

                    el.click();
                    return true;
                }
            }

            return false;

        });

        wait.until(d ->
                driver.getCurrentUrl().contains("inventory-item")
                        || driver.findElements(itemPageTitle).size() > 0
        );
    }

    public void waitInventoryReload() {

        waitUrlContains("inventory");

        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));

        wait.until(d -> driver.findElements(productNames).size() == 6);

        wait.until(ExpectedConditions.elementToBeClickable(productNames));
    }

    public void goToCart() {

        click(cartIcon);

        waitVisible(By.id("cart_contents_container"));
    }
}