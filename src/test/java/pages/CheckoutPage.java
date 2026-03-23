package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    private By firstName = By.cssSelector("[data-test='firstName']");
    private By lastName = By.cssSelector("[data-test='lastName']");
    private By postalCode = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By finishButton = By.cssSelector("[data-test='finish']");

    private By summaryContainer = By.id("checkout_summary_container");
    private By successMessage = By.className("complete-header");

    public void fillForm(String first, String last, String zip) {

        wait.until(d -> driver.getCurrentUrl().contains("checkout-step-one"));

        type(firstName, first);
        type(lastName, last);
        type(postalCode, zip);
    }

    public boolean isSummaryDisplayed() {

        wait.until(d -> driver.getCurrentUrl().contains("checkout-step-two"));

        wait.until(d -> driver.findElements(summaryContainer).size() > 0);

        return driver.findElements(finishButton).size() > 0;
    }

    public void finishPurchase() {

        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();

        wait.until(ExpectedConditions.urlContains("checkout-complete"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
    }

    public String getSuccessMessage() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

        return driver.findElement(successMessage).getText();
    }

    public void typeFirstName(String name) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).clear();
        driver.findElement(firstName).sendKeys(name);
    }

    public void typeLastName(String name) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).clear();
        driver.findElement(lastName).sendKeys(name);
    }

    public String getFirstNameValue() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));

        return driver.findElement(firstName).getAttribute("value");
    }

    public void continueCheckout() {

        click(continueButton);

        wait.until(d ->
                driver.getCurrentUrl().contains("checkout-step-two")
                        || driver.findElements(By.className("error-message-container")).size() > 0
        );
    }
}