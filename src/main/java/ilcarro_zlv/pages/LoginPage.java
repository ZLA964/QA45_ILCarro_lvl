package ilcarro_zlv.pages;

import ilcarro_zlv.dto.LoginUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.NoSuchElementException;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver webDrv) {
        setDriver(webDrv);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 3), this);
    }

    @FindBy(id = "email")
    WebElement inputEmail;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnYalla;

    @FindBy(xpath = "//span[@class='navigator']")
    WebElement btnNotRegistered;

    @FindBy(xpath = "//div[@class='login-registration-card']//h1")
    WebElement titleOfPage;

    @FindBy(xpath = "//span[@class='navigator']")
    WebElement linkIfNotRegistered;

    @FindBy(xpath = "//mat-dialog-container")
    WebElement popUpBox;

    @FindBy(xpath = "//h2[@class='message']")
    WebElement massageOnPopUp;

    @FindBy(xpath = "//mat-dialog-container//h1[@class='title']")
    WebElement titlePopUp;                                                  //  h1   2 time!       'Login failed'

    @FindBy(xpath = "//mat-dialog-container//button")
    WebElement btnPopUp;

    @FindBy(xpath = "//div[@class='error']")
    WebElement errorMessage;

    public boolean validateErrorMessage(String text){
        return isTextInElementPresent(errorMessage, text);
    }

    public void clickOnLinkIfNotRegistered() {
        //       pause(1);
        linkIfNotRegistered.click();
    }

    public boolean isLoginPage() {
        return "title".equals(titleOfPage.getAttribute("class"))
                && "Log in".equals(titleOfPage.getText());
    }

    public void typeLoginForm(LoginUser user) {
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnYalla() {
        if (btnYalla.isEnabled()) {
            btnYalla.click();
        } else {
            throw new NoSuchElementException("Сan't press the button Y'alla! " +
                    "Wrong email at all!");
        }
    }

    public void clickOnDisabledBtnYalla(){
        btnYalla.click();
    }


    public boolean isBtnYallaDisable() {
        return !btnYalla.isEnabled();
    }

    public void logIn(LoginUser user) {
        typeLoginForm(user);
        clickBtnYalla();
    }

    public void clickButtonPopUp() {
        btnPopUp.click();
    }

    public boolean isLoginSuccess() {
        waitForPopup(popUpBox, 2);
        waitForPopup(massageOnPopUp, 1);
        return isTextInElementPresent(massageOnPopUp, "Logged in success");
    }

    public boolean isLoginIncorrect() {
        waitForPopup("//mat-dialog-container", 5);
        waitForPopup(titlePopUp, 1);
        return isTextInElementPresent(titlePopUp, "Login failed");
    }


}
