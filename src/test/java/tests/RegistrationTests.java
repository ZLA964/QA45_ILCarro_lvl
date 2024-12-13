package tests;


import ilcarro.dto.UserDtoLombok;
import ilcarro.manager.ApplicationManager;
import ilcarro.pages.RegistrationPage;
import ilcarro.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class RegistrationTests extends ApplicationManager {
    RegistrationPage registrationPage;

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
        registrationPage =new RegistrationPage(getDriver());
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBox();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage.isPopUpMessagePresent());
    }

    @Test
    void registrationNegativeTest_wrongEmail(){
        int i  = new Random().nextInt(1000)+1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .name("Bob")
                .lastName("Doe")
                .email(i+"bob_doe_mail.com")
                .password("Pass123!")
                .build();
        System.out.println(user);
        new SearchPage(getDriver()).clickBtnSignUl();
        registrationPage =new RegistrationPage(getDriver());
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBox();
  //      registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage.validationErrorMessage("Wrong email format"));
    }

    @Test
    void registrationNegativeTest_emptyLastName(){
        int i  = new Random().nextInt(1000)+1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .name("Bob")
                .lastName("")
                .email(i+"bob_doe@mail.com")
                .password("Pass123!")
                .build();
        System.out.println(user);
        new SearchPage(getDriver()).clickBtnSignUl();
        registrationPage =new RegistrationPage(getDriver());
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBox();
        //      registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage.validationErrorMessage("Last name is required"));
    }

    @Test
    void registrationNegativeTest_lastNameSpace(){
        int i  = new Random().nextInt(1000)+1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .name("Bob")
                .lastName(" ")
                .email(i+"bob_doe@mail.com")
                .password("Pass123!")
                .build();
        System.out.println(user);
        new SearchPage(getDriver()).clickBtnSignUl();
        registrationPage =new RegistrationPage(getDriver());
        registrationPage.typeRegistrationForm(user);
        registrationPage.clickCheckBox();
        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage.isPopUpMessagePresent("must not be"));
    }

    @Test
    void registrationNegativeTest_lnoChekBox(){
        int i  = new Random().nextInt(1000)+1000;
        UserDtoLombok user = UserDtoLombok.builder()
                .name("Bob")
                .lastName("Doe")
                .email(i+"bob_doe@mail.com")
                .password("Pass123!")
                .build();
        System.out.println(user);
        new SearchPage(getDriver()).clickBtnSignUl();
        registrationPage =new RegistrationPage(getDriver());
        registrationPage.typeRegistrationForm(user);

        registrationPage.clickBtnYalla();
        Assert.assertTrue(registrationPage.btnYallaDisable());
    }

}
