package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By firstName = By.cssSelector("[data-test='firstName']");
    private By lastName = By.cssSelector("[data-test='lastName']");
    private By postalCode = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By finishButton = By.cssSelector("[data-test='finish']");

    private By summaryContainer = By.id("checkout_summary_container");

    private By successMessage = By.className("complete-header");

    public void fillForm(String first, String last, String zip) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(first);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).sendKeys(last);
        wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode)).sendKeys(zip);

        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public boolean isSummaryDisplayed() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryContainer));
        return driver.getCurrentUrl().contains("checkout-step-two");
    }

    public void finishPurchase() {

        wait.until(
                ExpectedConditions.elementToBeClickable(finishButton)
        ).click();
    }

    public String getSuccessMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage)
        ).getText();
    }

    public void typeFirstName(String name){

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        );

        field.clear();
        field.sendKeys(name);
    }

    public void typeLastName(String name){

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastName)
        );

        field.clear();
        field.sendKeys(name);
    }

    public String getFirstNameValue(){

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        ).getAttribute("value");
    }
}