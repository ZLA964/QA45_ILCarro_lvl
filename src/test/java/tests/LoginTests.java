package tests;

import ilcarro.dto.UserDtoLombok;
import ilcarro.manager.ApplicationManager;
import ilcarro.pages.LoginPage;
import ilcarro.pages.SearchPage;
import ilcarro.utils.TestNGListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static ilcarro.utils.PropertiesReader.getProperty;

@Listeners(TestNGListener.class)
public class LoginTests extends ApplicationManager {

    LoginPage loginPage;

    @BeforeMethod
    public void goToLoginPage(){
        new SearchPage(getDriver()).clickBtnLogIn();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void loginPositiveTest(){
        UserDtoLombok user = UserDtoLombok.builder()
                .email(getProperty("login.properties", "email"))
                .password(getProperty("login.properties", "password"))
///                .email("user837@mail.com")
///                .password("Pass-837-word!")
                .build();
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(loginPage.isPopUpLoginMessagePresent("Logged in success"));
    }

    @Test
    public void loginNegativeTest_wrongEmail(){
        UserDtoLombok user = UserDtoLombok.builder()
                .email("user837mail.com")
                .password("Pass-837-word!")
                .build();
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(loginPage.validateErrorMessage("It'snot look like email"));
    }

    @Test
    public void loginNegativeTest_emptyPassword(){
        UserDtoLombok user = UserDtoLombok.builder()
                .email("user837@mail.com")
                .password("")
                .build();
        System.out.println(user);
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        Assert.assertTrue(loginPage.validateErrorMessage("Password is required"));
    }
}
