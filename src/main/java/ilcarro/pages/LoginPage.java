package ilcarro.pages;

import ilcarro.dto.UserDtoLombok;
import ilcarro.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver webDrv) {
        setDriver(webDrv);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(id = "email")
    WebElement inputEmail;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnYalla;

//    @FindBy(xpath = "//h2[@class='message']")
//    WebElement popUpMessage;

    @FindBy(xpath = "//div[@class='error']")
    WebElement errorMessage;

    @FindBy(id = "1")   //a[@ng-reflect-router-link='let-car-work']
    WebElement btnLetCarWork;
    @FindBy(xpath = "//button[@type='button']")
    WebElement btnOk;

    @FindBy(xpath = "//div[@class='cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing']")
    WebElement popUpWindows;

    public void clickBtnOK() {
       clickWait(btnOk, 3);
     }


    public void clickBtnLetCarWork() {
     //   pause(1);
 //      waitNewElementOnPage(popUpWindows,3);
 //      waitNewElementIsNotOnPage(popUpWindows,3);
 //       btnLetCarWork.click();
        isElementPresentDOM("//a[@ng-reflect-router-link='let-car-work']",3);
        clickWait(btnLetCarWork, 3);
        System.out.println("second click on btnLetCarWork ");
        clickWait(btnLetCarWork, 3);
    }

    public void typeLoginForm(UserDtoLombok user) {
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnYalla() {
        btnYalla.click();
    }

    public boolean isPopUpLoginMessagePresent(String text){
        isElementPresentDOM("//h2[@class='message']",5);
        return isTextInElementPresent(popUpMessage, text);
    }

    public boolean validateErrorMessage(String text){
        return isTextInElementPresent(errorMessage, text);
    }


}
