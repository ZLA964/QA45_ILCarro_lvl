package tests;

import dto.UserDto;
import manager.ApplManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.SignUpPage;

import java.util.Random;

public class RegistrationTests extends ApplManager {
    UserDto user;

    @BeforeMethod
    void setUpUser(){
        int randomNum = new Random().nextInt(1000);
        int i = new Random().nextInt(1000);
        user = new UserDto("User", "Lastuser",
                "user"+i+"@mail.com", "Pass-"+i+"-word!");
    }

    @Test
    void registrationPositiveTest(){

        new HomePage(getDriver()).clickBtnSignUl();
        new SignUpPage(getDriver()).inputUserData(user.getUserData());
        BasePage.pause(10);
 //       new SignUpPage(getDriver()).userRegistration(user);
    }
}
