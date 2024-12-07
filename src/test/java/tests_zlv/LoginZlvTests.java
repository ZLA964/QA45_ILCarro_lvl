package tests_zlv;

import ilcarro_zlv.dto.LoginUser;
import ilcarro_zlv.manager.ApplManager;
import ilcarro_zlv.pages.HomePage;
import ilcarro_zlv.pages.LoginPage;

import ilcarro_zlv.pages.SignUpPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

public class LoginZlvTests extends ApplManager {
    LoginUser user;
    LoginPage loginPage;

    @BeforeMethod
    void openLoginPage() {
        new HomePage(getDriver()).clickBtnLogIn();
        loginPage = new LoginPage(getDriver());
        String email = "user837@mail.com";
        String password = "Pass-837-word!";
        user = LoginUser.builder()
                .email(email)
                .password(password)
                .build();

    }

    @Test
    void loginPositiveTest() {
        System.out.println(user);
        loginPage.logIn(user);
        Assert.assertTrue(loginPage.isLoginSuccess());
    }

    @Test
    void loginNegativeWrongPasswordTest() {
        user.setPassword("Pas-937-word!");
        System.out.println(user);
        loginPage.logIn(user);
        Assert.assertTrue(loginPage.isLoginIncorrect());
    }

    @Test
    void loginNegativeWrongEmailTest() {
        user.setEmail("user037mail.com");
        System.out.println(user);
        loginPage.logIn(user);
        Assert.assertTrue(loginPage.isLoginIncorrect());
    }

    @Test
    void loginNegativeInvalidEmailTest() {  // email without '@'
        user.setEmail("user837mail.com");
        System.out.println(user);
        try {
            loginPage.logIn(user);
            Assert.fail("Expected NoSuchElementException was not thrown.");
            // If the exception is not thrown, the test will fail.
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().contains("Wrong email"), "Exception message doesn't match");
            Assert.assertTrue(loginPage.isBtnYallaDisable(), "Yalla button should be disabled.");
        }
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    void loginNegativeInvalidEmailVar2Test() {  // email without '@'
        user.setEmail("user837mail.com");
        System.out.println(user);
        loginPage.logIn(user);
    }

    @Test
    void loginNegativeTest_checkLinkNotRegisted(){
        loginPage.clickOnLinkIfNotRegistered();
        boolean isOpenRegistrationPage = new SignUpPage(getDriver()).isSignUpPage();
        Assert.assertTrue(isOpenRegistrationPage);
    }

}
// {"email": "user837@mail.com", "password": "Pass-837-word!"}