package ilcarro.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class SearchPage extends BasePage{

    public SearchPage(WebDriver webDrv){
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

    @FindBy(xpath ="//a[text()=' Log in ']" )
    WebElement btnLogIn;

    @FindBy(id = "city")
    WebElement inputCity;

    @FindBy(id = "dates")
    WebElement inputDates;

    @FindBy(xpath = "//div[contains(@class, 'error')]")
    WebElement errorMessage;

    public boolean isErrorMessage(String text){
        try {
            waitNewElementOnPage(errorMessage,5);
            return isTextInElementPresent(errorMessage,text);
        } catch (TimeoutException e) {
            System.out.println("Wait errorMessage threw exception");
            return false;
        }

    }





    public void clickBtnLogIn(){
        btnLogIn.click();
    }


//    public void fillSearchCarFormWOCalendar(String city, String startDate, String endDate) {
//        inputCity.click();
//        inputCity.sendKeys(city);
//        Actions actions = new Actions(driver);
//        actions.moveToElement(inputCity,0,27).pause(2000).click().perform();
//        inputDates.click();
//        inputDates.sendKeys(startDate+" - "+ endDate);
//        inputDates.sendKeys(Keys.ENTER);
//    }

    public void fillSearchCarFormWOCalendar(String city, String startDate, String endDate) {
        inputCity.click();
        inputCity.sendKeys(city);
        Actions actions = new Actions(driver);
        actions.moveToElement(inputCity, 0, 27).pause(2000).click().perform();
        //=======================================
        inputDates.click();
        inputDates.sendKeys(startDate + " - " + endDate);
        inputDates.sendKeys(Keys.ENTER);
    }

}
