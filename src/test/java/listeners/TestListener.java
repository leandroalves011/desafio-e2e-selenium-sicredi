package listeners;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class TestListener implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {

        boolean failed =
                context.getExecutionException().isPresent();

        if (!failed) return;

        WebDriver driver = getDriver(context);

        if (driver == null) return;

        if (driver instanceof TakesScreenshot) {

            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Failure Screenshot",
                    new ByteArrayInputStream(screenshot)
            );
        }
    }

    private WebDriver getDriver(ExtensionContext context) {

        try {

            Object testInstance =
                    context.getRequiredTestInstance();

            return (WebDriver)
                    testInstance
                            .getClass()
                            .getSuperclass()
                            .getMethod("getDriver")
                            .invoke(testInstance);

        } catch (Exception e) {

            return null;
        }
    }
}