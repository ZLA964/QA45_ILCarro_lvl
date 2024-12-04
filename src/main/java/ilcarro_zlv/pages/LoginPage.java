package ilcarro_zlv.pages;

import ilcarro_zlv.dto.LoginUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.NoSuchElementException;


public class LoginPage extends BasePage{

    public LoginPage(WebDriver webDrv){
        setDriver(webDrv);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,1), this);
    }

    @FindBy(id = "email")
    WebElement inputEmail;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnYalla;

    @FindBy(xpath = "//span[@class='navigator']")
    WebElement btnNotRegistered;

    public void typeLoginForm(LoginUser user){
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnYalla() {
        if(btnYalla.isEnabled()) {
            btnYalla.click();
        } else {
            throw new NoSuchElementException("Сan't press the button Y'alla! " +
                    "Wrong email at all!");
        }
    }

    public void logIn(LoginUser user){
        typeLoginForm(user);
        clickBtnYalla();
    }

    @FindBy(xpath = "//mat-dialog-container")
    WebElement popUpBox;

    @FindBy(xpath = "//h2[@class='message']")
    WebElement massageOnPopUp;

    @FindBy(xpath = "//mat-dialog-container//h1[@class='title']")
    WebElement titlePopUp;


    public boolean isLoginSuccess(){
        waitForPopup(popUpBox, 7);
        waitForPopup(massageOnPopUp,2);
        return isTextInElementPresent(massageOnPopUp, "Logged in success");
    }
    //  'Logged in success'
    //  "Login or Password incorrect"
    // h1 'Login failed'

    public  boolean isLoginIncorrect(){
        waitForPopup(popUpBox,7);
        waitForPopup(titlePopUp,7);
 //       System.out.println(titlePopUp.getText());
        return isTextInElementPresent(titlePopUp, "Login failed");
    }
}
