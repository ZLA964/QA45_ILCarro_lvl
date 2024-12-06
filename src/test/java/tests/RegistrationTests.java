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
}
