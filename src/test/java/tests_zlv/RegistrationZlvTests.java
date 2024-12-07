package tests_zlv;

import ilcarro_zlv.dto.UserDto;
import ilcarro_zlv.pages.HomePage;
import ilcarro_zlv.pages.LoginPage;
import ilcarro_zlv.pages.RegistrationOkeyPage;
import ilcarro_zlv.pages.SignUpPage;
import ilcarro_zlv.manager.ApplManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

public class RegistrationZlvTests extends ApplManager {
    UserDto userLvL;

    @BeforeMethod
    void setUpUser() {
//        int randomNum = new Random().nextInt(1000);
        int i = new Random().nextInt(1000);
        userLvL = new UserDto("User", "Lastuser",
                "user" + i + "@mail.com", "Pass-" + i + "-word!");
    }

    @Test
    void registrationPositiveTest() {

        new HomePage(getDriver()).clickBtnSignUl();
        System.out.println("{\"email\": \"" + userLvL.getEmail() + "\", \"password\": \"" + userLvL.getPassword() + "\"}");
//      json can write to file for next delete users.
        new SignUpPage(getDriver()).userRegistration(userLvL);
        Assert.assertTrue(new RegistrationOkeyPage(getDriver()).isRegistrationOkey());
    }

    @Test
    void negativeRegistrationTest_checkLinkAlreadyRegistered(){
        new HomePage(getDriver()).clickBtnSignUl();
        new SignUpPage(getDriver()).clickOnLinkIfAlreadyRegistered();
        boolean isOpenLoginPage = new LoginPage(getDriver()).isLoginPage();
        Assert.assertTrue(isOpenLoginPage);
    }

}
