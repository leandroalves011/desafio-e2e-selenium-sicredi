package core;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

public class PageManager {

    private WebDriver driver;

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    //utilizado para evitar criar locators e pageObjects diretamente nos testes

    public PageManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage loginPage() {

        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }

        return loginPage;
    }

    public InventoryPage inventoryPage() {

        if (inventoryPage == null) {
            inventoryPage = new InventoryPage(driver);
        }

        return inventoryPage;
    }

    public CartPage cartPage() {

        if (cartPage == null) {
            cartPage = new CartPage(driver);
        }

        return cartPage;
    }

    public CheckoutPage checkoutPage() {

        if (checkoutPage == null) {
            checkoutPage = new CheckoutPage(driver);
        }

        return checkoutPage;
    }

}