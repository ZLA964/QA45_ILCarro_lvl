package ilcarro.pages;

import ilcarro.dto.UserDtoLombok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegistrationPage extends BasePage {


    public RegistrationPage(WebDriver webDrv) {
        setDriver(webDrv);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(id = "name")
    WebElement inputName;

    @FindBy(id = "lastName")
    WebElement inputLastName;

    @FindBy(id = "email")
    WebElement inputEmail;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(xpath = "//label[@for='terms-of-use']")
    private WebElement checkBox;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnYalla;

    public void clickBtnYalla() {
        btnYalla.click();
    }

    @FindBy(xpath = "//h2[@class='message']")
    WebElement popUpMessage;

    public boolean isPopUpMessagePresent() {
//        pause(3);
        return isTextInElementPresent(popUpMessage, "You are logged in success");
    }

    public boolean isPopUpMessagePresent(String text) {
 //       pause(3);
        return isTextInElementPresent(popUpMessage, text);
    }

    @FindBy(xpath = "//div[@class='error']")
    WebElement errorMessage;

    public boolean validationErrorMessage(String text){
        return isTextInElementPresent(errorMessage, text);
    }

    public void typeRegistrationForm(UserDtoLombok user) {
        inputName.sendKeys(user.getFirstName());
        inputLastName.sendKeys(user.getLastName());
        inputEmail.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
    }

    public void clickCheckBox() {
        System.out.println(checkBox.getRect().getWidth() + "X" + checkBox.getRect().getHeight());
        int widthCheckBox = checkBox.getRect().getWidth();
        int heightCheckBox = checkBox.getRect().getHeight();
        System.out.println("width: " + widthCheckBox+" height: "+ heightCheckBox);
        Actions actions = new Actions(driver);
        actions.moveToElement(checkBox, -widthCheckBox / 3, -heightCheckBox / 4).click().perform();
    }

    public boolean btnYallaDisable(){
        return !btnYalla.isEnabled();
    }

}


