package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage{

    public HomePage(WebDriver webDrv){
        setDriver(webDrv);
        driver.get("https://ilcarro.web.app/search");
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver,10), this);
    }

    @FindBy(xpath = "//a[@ng-reflect-router-link='registration']")
    WebElement btnSignUp;
    public void clickBtnSignUl(){
        btnSignUp.click();
    }




}
