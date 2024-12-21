package tests;

import ilcarro.dto.CarDto;
import ilcarro.dto.UserDtoLombok;
import ilcarro.manager.ApplicationManager;
import ilcarro.pages.LetCarWorkPage;
import ilcarro.pages.LoginPage;
import ilcarro.pages.SearchPage;

import ilcarro.utils.Fuel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class AddNewCarTest extends ApplicationManager {
    LoginPage loginPage;
    LetCarWorkPage letCarWorkPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void login() {
        UserDtoLombok user = UserDtoLombok.builder()
                .email("user837@mail.com")
                .password("Pass-837-word!")
                .build();
        new SearchPage(getDriver()).clickBtnLogIn();
        loginPage = new LoginPage(getDriver());
        loginPage.typeLoginForm(user);
        loginPage.clickBtnYalla();
        if (loginPage.isPopUpLoginMessagePresent("Logged in success")) {
            System.out.println("Login success");
            loginPage.clickBtnOK();
            loginPage.clickBtnLetCarWork();
        } else {
            System.out.println("Something wrong on Login");
        }
    }

    @Test
    public void addNewCarPositiveTest() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("Mazda")
                .model("CX-90")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        String expectedMessage = car.getManufacture() + " " + car.getModel() + " added successful";
        System.out.println(expectedMessage);
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm(car);
        Assert.assertTrue(letCarWorkPage.isPopUpMessagePresent(expectedMessage));
    }

    @Test
    public void addNewCarNegativeTest_noLocation() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("")
                .manufacture("Mazda")
                .model("CX-90")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm_forNegativeTest(car);
        Assert.assertTrue(  letCarWorkPage.isBtnSubmitDisabled(3));
    }

    @Test
    public void addNewCarNegativeTest_noLocationV2() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("")
                .manufacture("Mazda")
                .model("CX-90")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        String expectedMessage = "Wrong address";
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm_forNegativeTest(car);
        softAssert.assertTrue(  letCarWorkPage.isBtnSubmitDisabled(2));
        softAssert.assertTrue(letCarWorkPage.isErrorMessagePresent(expectedMessage));
        softAssert.assertAll();
    }

    @Test
    public void addNewCarNegativeTest_noManufacture() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("")
                .model("CX-90")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm_forNegativeTest(car);
        Assert.assertTrue(  letCarWorkPage.isBtnSubmitDisabled(3));
    }

    @Test
    public void addNewCarNegativeTest_noManufactureV2() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("")
                .model("CX-90")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        String expectedMessage = "Make is required";
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm_forNegativeTest(car);
        softAssert.assertTrue(  letCarWorkPage.isBtnSubmitDisabled(2));
        softAssert.assertTrue(letCarWorkPage.isErrorMessagePresent(expectedMessage));
        softAssert.assertAll();
    }

    @Test
    public void addNewCarNegativeTest_noFuelV2() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("Mazda")
                .model("CX-90")
                .year("2022")
                .fuel(null)
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        String expectedMessage = "Fuel is required";
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm_forNegativeTest(car);
        softAssert.assertTrue(  letCarWorkPage.isBtnSubmitDisabled(2));
        softAssert.assertTrue(letCarWorkPage.isErrorMessagePresent(expectedMessage));
        softAssert.assertAll();
    }
}