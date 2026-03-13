
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    private By username = By.cssSelector("[data-test='username']");
    private By password = By.cssSelector("[data-test='password']");
    private By loginButton = By.cssSelector("[data-test='login-button']");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public void login(String user,String pass){

        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(user);

        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);

        driver.findElement(loginButton).click();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }
}
