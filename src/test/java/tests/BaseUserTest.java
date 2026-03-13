package tests;

import base.BaseTest;
import core.PageManager;
import listeners.TestListener;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.UserType;

@ExtendWith(TestListener.class)
public class BaseUserTest extends BaseTest {

    protected PageManager pages;

    protected void initializePages() {
        pages = new PageManager(driver);
    }

    protected void login(UserType user) {

        pages.loginPage()
                .login(user.getUsername(), "secret_sauce");

    }

}