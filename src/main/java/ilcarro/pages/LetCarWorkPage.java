package ilcarro.pages;

import ilcarro.dto.CarDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LetCarWorkPage extends BasePage {

    public LetCarWorkPage(WebDriver webDrv) {
        setDriver(webDrv);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 3), this);
    }

    @FindBy(id = "pickUpPlace")
    WebElement inputLocation;
    @FindBy(xpath = "//div[@class='pac-item']")    //div[@class='pac-item
    WebElement locationSubmit;
    @FindBy(id = "make")
    WebElement inputManufacture;

    @FindBy(id = "model")
    WebElement inputModel;
    @FindBy(id = "year")
    WebElement inputYear;

    @FindBy(id = "fuel")
    WebElement inputFuel;

    @FindBy(id = "seats")
    WebElement inputSeats;
    @FindBy(id = "class")
    WebElement inputClass;
    @FindBy(id = "serialNumber")
    WebElement inputSerialNumber;
    @FindBy(id = "price")
    WebElement inputPrice;
    @FindBy(id = "about")
    WebElement inputAbout;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;
    //   @FindBy(xpath = "//h2[@class='message']")
    //   WebElement popUpMessage;

    @FindBy(xpath = "//div[contains(@class, 'error')]")
    WebElement errorMessage;

    public boolean isErrorMessagePresent(String text) {
        return isTextInElementPresent(errorMessage, text);
    }

    public boolean isPopUpMessagePresent(String text) {
        return isTextInElementPresent(popUpMessage, text);
    }

    public void typeLetCarWorkForm(CarDto car) {
        inputLocation.sendKeys(car.getCity());
        locationSubmit.click();
//        clickWait(locationSubmit, 1);  // not need clickWait , .click enough.
        inputManufacture.sendKeys(car.getManufacture());
        inputModel.sendKeys(car.getModel());
        inputYear.sendKeys(car.getYear());
        //==============
        inputFuel.click();
        clickWait(driver.findElement(By.xpath(car.getFuel())), 3);
        inputFuel.click();
//        inputFuel.sendKeys(Keys.ESCAPE);
        inputSeats.sendKeys(car.getSeats() + "");
        inputClass.sendKeys(car.getCarClass());
        inputSerialNumber.sendKeys(car.getSerialNumber());
        inputPrice.sendKeys(Double.toString(car.getPricePerDay()));
        inputAbout.sendKeys(car.getAbout());
        clickWait(btnSubmit, 5);

    }


    public void typeLetCarWorkForm_forNegativeTest(CarDto car) {
        if (!car.getCity().isBlank()) {
            inputLocation.sendKeys(car.getCity());
  //          locationSubmit.click();
            clickWait(locationSubmit, 5);
        } else {
            inputLocation.click();
        }
        if (!car.getManufacture().isBlank()) {
            inputManufacture.sendKeys(car.getManufacture());
        } else {
            inputManufacture.click();
        }
        inputModel.sendKeys(car.getModel());
        inputYear.sendKeys(car.getYear());
        //==============
        if(car.getFuel() != null) {
            inputFuel.click();
            clickWait(driver.findElement(By.xpath(car.getFuel())), 5);
            inputFuel.click();
        } else {
            inputFuel.click();
        }
//        inputFuel.sendKeys(Keys.ESCAPE);
        inputSeats.sendKeys(car.getSeats() + "");
        inputClass.sendKeys(car.getCarClass());
        inputSerialNumber.sendKeys(car.getSerialNumber());
        inputPrice.sendKeys(Double.toString(car.getPricePerDay()));
        inputAbout.sendKeys(car.getAbout());
        //       clickWait(btnSubmit, 5);
    }

    public boolean isBtnSubmitDisabled(int timeInSeconds) {
        try {
            return isWebElementDisabled(btnSubmit, timeInSeconds);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}


