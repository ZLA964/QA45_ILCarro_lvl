package tests;

import com.sun.source.tree.AssertTree;
import dto.UserDto;
import manager.ApplManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.RegistrationOkeyPage;
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
//        new SignUpPage(getDriver()).inputUserData(user.getUserData());
//        new SignUpPage((getDriver())).setCheckBoxTermsOfUse();
//        new SignUpPage(getDriver()).submitRegistration();
//        BasePage.pause(2);
       new SignUpPage(getDriver()).userRegistration(user);
       Assert.assertTrue(new RegistrationOkeyPage(getDriver()).isRegistrationOkey());
       System.out.println("user is registered:");
       System.out.println("{\"email\": \"" + user.getEmail() + "\", \"password\": \"" + user.getPassword() + "\"}");
// json can write to file for next delete users.
    }
}
