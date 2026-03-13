package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SortOption;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        return driver.findElement(inventoryContainer).isDisplayed();
    }

    public boolean allImagesAreEqual() {

        List<WebElement> images = driver.findElements(productImages);

        if (images.isEmpty()) return false;

        String firstSrc = images.get(0).getAttribute("src");

        for (WebElement img : images) {
            if (!img.getAttribute("src").equals(firstSrc)) {
                return false;
            }
        }

        return true;
    }

    public void addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addBackpack)).click();
    }

    public void addBikeLightToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addBikeLight)).click();
    }

    public void addBoltTShirtToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addBoltTShirt)).click();
    }

    public void addFleeceJacketToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addFleeceJacket)).click();
    }

    public void addOnesieToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addOnesie)).click();
    }

    public void addRedTshirtToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addRedTshirt)).click();
    }

    public void removeFirstProductFromCart() {
        driver.findElement(By.cssSelector("[data-test^='remove']")).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public int getCartItemCount() {

        List<WebElement> badges = driver.findElements(cartBadge);

        if (badges.isEmpty()) return 0;

        return Integer.parseInt(badges.get(0).getText());
    }

    public List<String> getProductNames() {

        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void sortProducts(SortOption option) {

        driver.findElement(sortDropdown)
                .sendKeys(option.getValue());
    }

    public void logout() {

        driver.findElement(menuButton).click();
        driver.findElement(logoutLink).click();
    }

    public void clickAbout() {

        driver.findElement(menuButton).click();
        driver.findElement(aboutLink).click();
    }

    public boolean isOnSauceLabsPage() {
        return driver.getCurrentUrl().contains("saucelabs");
    }

    public void openItemByIndex(int index) {
        driver.findElements(productNames).get(index).click();
    }

    public void addItemFromItemPage() {
        driver.findElement(itemPageAddButton).click();
    }

    public String getProductNameByIndex(int index) {
        return driver.findElements(productNames).get(index).getText();
    }

    public String getItemPageTitle() {
        return driver.findElement(itemPageTitle).getText();
    }
}