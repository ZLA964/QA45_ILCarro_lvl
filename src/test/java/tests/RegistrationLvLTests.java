package tests;

import dto.UserDto;
import dto.UserDtoLombok;
import manager.ApplManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.util.Random;

public class RegistrationLvLTests extends ApplManager {
    UserDto userLvL;

    @BeforeMethod
    void setUpUser(){
//        int randomNum = new Random().nextInt(1000);
        int i = new Random().nextInt(1000);
        userLvL = new UserDto("User", "Lastuser",
                "user"+i+"@mail.com", "Pass-"+i+"-word!");
    }

    @Test
    void registrationPositiveLvLTest(){

        new HomePage(getDriver()).clickBtnSignUl();
//        new SignUpPage(getDriver()).inputUserData(user.getUserData());
//        new SignUpPage((getDriver())).setCheckBoxTermsOfUse();
//        new SignUpPage(getDriver()).submitRegistration();
//        BasePage.pause(2);
//        System.out.println("user is registered:");

        System.out.println("{\"email\": \"" + userLvL.getEmail() + "\", \"password\": \"" + userLvL.getPassword() + "\"}");
//      json can write to file for next delete users.

       new SignUpPage(getDriver()).userRegistration(userLvL);
       Assert.assertTrue(new RegistrationOkeyPage(getDriver()).isRegistrationOkey());

    }

    @Test
    void registrationPositiveTest(){
        int i  = new Random().nextInt(1000)+1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .name("Bob")
                .lastName("Doe")
                .email(i+"bob_doe@mail.com")
                .password("Pass123!")
                .build();
        System.out.println(user);
        new SearchPage(getDriver()).clickBtnSignUl();
        RegistrationPage registrationPage =new RegistrationPage(getDriver());
        registrationPage.typeRegistrationForm(user);

    }
}
