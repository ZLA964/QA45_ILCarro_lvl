package ilcarro.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static ilcarro.utils.PropertiesReader.*;


public class SearchPage extends BasePage {

    public SearchPage(WebDriver webDrv) {
        setDriver(webDrv);
//        driver.get("https://ilcarro.web.app/search");
        driver.get(getProperty("login.properties", "urlStart"));
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[@ng-reflect-router-link='registration']")
    WebElement btnSignUp;

    public void clickBtnSignUl() {
        btnSignUp.click();
    }

    @FindBy(xpath = "//a[text()=' Log in ']")
    WebElement btnLogIn;

    @FindBy(id = "city")
    WebElement inputCity;

    @FindBy(id = "dates")
    WebElement inputDates;

    @FindBy(xpath = "//div[contains(@class, 'error')]")
    WebElement errorMessage;

    public boolean isErrorMessage(String text) {
        try {
            waitNewElementOnPage(errorMessage, 5);
            return isTextInElementPresent(errorMessage, text);
        } catch (TimeoutException e) {
            System.out.println("Wait errorMessage threw exception");
            return false;
        }

    }


    public void clickBtnLogIn() {
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

    // =================calendar

    @FindBy(xpath = "//button[@aria-label='Choose month and year']")
    WebElement buttonMonthYear;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;


    public void fillSearchCarFormWithCalendar(String city, String startDate, String endDate) {
        inputCity.click();
        inputCity.sendKeys(city);
        Actions actions = new Actions(driver);
        actions.moveToElement(inputCity, 0, 27).pause(2000).click().perform();
        //=======================================
        inputDates.click();
        String[] startDayArray = startDate.split("/"); // 03/25/2025 -> [03][25][2025]
        String[] endDayArray = endDate.split("/");
        typeYearMonthDay(startDayArray[2], startDayArray[0], startDayArray[1]);  //. year, month, day. 03/25/2025
        typeYearMonthDay(endDayArray[2], endDayArray[0], endDayArray[1]);
        clickWait(btnSubmit,5);


    }

    private void typeYearMonthDay(String year, String month, String day) {
        buttonMonthYear.click();
        //div[contains(text(), 2025)]
        driver.findElement(By.xpath(
                "//div[contains(text(), '" + year + "')]")).click();
        //div[contains(text(), 'DEC')]
        driver.findElement(By.xpath(
                "//div[contains(text(), '" + month.toUpperCase() + "')]")).click();
        driver.findElement(By.xpath(
                "//div[contains(text(), '" + day + "')]")).click();
    }


}
