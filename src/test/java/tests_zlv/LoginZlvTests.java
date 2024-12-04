package tests_zlv;

import ilcarro_zlv.dto.LoginUser;
import ilcarro_zlv.manager.ApplManager;
import ilcarro_zlv.pages.HomePage;
import ilcarro_zlv.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginZlvTests extends ApplManager {
    LoginUser user;
    LoginPage loginPage;

    private void setUser(String email, String password){
      user = LoginUser.builder()
              .email(email)
              .password(password)
              .build();
    }

    @BeforeMethod
    void openLoginPage(){
        new HomePage(getDriver()).clickBtnLogIn();
        loginPage = new LoginPage(getDriver());
        setUser("user837@mail.com", "Pass-837-word!");
    }


    @Test
    void loginPositiveTest(){
        System.out.println(user);
        loginPage.logIn(user);
        Assert.assertTrue(loginPage.isLoginSuccess());
    }

    @Test
    void loginNegativeInvalidPasswordTest(){
        user.setPassword("Pas--837-word!");
        System.out.println(user);
        loginPage.logIn(user);
        Assert.assertTrue(loginPage.isLoginIncorrect());
    }


}

// {"email": "user837@mail.com", "password": "Pass-837-word!"}
// Logged in success
