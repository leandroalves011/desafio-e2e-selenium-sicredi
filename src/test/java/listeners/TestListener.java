package listeners;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class TestListener implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {

        boolean testFailed = context.getExecutionException().isPresent();

        if (testFailed && BaseTest.driver instanceof TakesScreenshot) {

            byte[] screenshot = ((TakesScreenshot) BaseTest.driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Screenshot - " + context.getDisplayName(),
                    new ByteArrayInputStream(screenshot)
            );
        }
    }
}