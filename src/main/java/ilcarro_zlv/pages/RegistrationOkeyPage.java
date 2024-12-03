package ilcarro_zlv.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


public class RegistrationOkeyPage extends BasePage {

    public RegistrationOkeyPage(WebDriver webDrv) {
        setDriver(webDrv);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//button[text()='Ok']")
    WebElement btnOkey;

    @FindBy(xpath = "//h2[text()='You are logged in success']")
    WebElement registrationIsOkey;

    public boolean isRegistrationOkey(){
        pause(1);
        if(!registrationIsOkey.isDisplayed()) {
            return false;
        }
        pause(1);
        btnOkey.click();
        return true;
    }

}
