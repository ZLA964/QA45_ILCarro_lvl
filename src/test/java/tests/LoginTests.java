package tests;

import ilcarro.dto.UserDtoLombok;
import ilcarro.manager.ApplicationManager;
import ilcarro.pages.LoginPage;
import ilcarro.pages.SearchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
                .email("user837@mail.com")
                .password("Pass-837-word!")
                .build();
        loginPage.typeLoginForm(user);

    }
}
