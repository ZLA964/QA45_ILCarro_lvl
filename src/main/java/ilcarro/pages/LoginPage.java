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

    @FindBy(xpath = "//h2[@class='message']")
    WebElement popUpMessage;

    @FindBy(xpath = "//div[@class='error']")
    WebElement errorMessage;

    public void typeLoginForm(UserDtoLombok user) {
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickBtnYalla() {
        btnYalla.click();
    }

    public boolean isPopUpLoginMessagePresent(String text){
        return isTextInElementPresent(popUpMessage, text);
    }

    public boolean validateErrorMessage(String text){
        return isTextInElementPresent(errorMessage, text);
    }


}
