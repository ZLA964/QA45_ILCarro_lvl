package ilcarro_zlv.pages;

import ilcarro_zlv.dto.UserDto;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SignUpPage extends BasePage {

    public SignUpPage(WebDriver webDrv) {
        setDriver(webDrv);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//form//input")
    private List<WebElement> inputElements;

    @FindBy(xpath = "//label[@for='terms-of-use']")
    private WebElement labelCheckBox;

    @FindBy(xpath = "//button[text()='Y’alla!']")
    WebElement btnSubmit;

    @FindBy(xpath = "//span[text()='Click here']")
    WebElement btnAlreadyRegistered;

    @FindBy(xpath = "//button[text()='Ok']")
    WebElement btnRegistrationOkey;

    @FindBy(xpath = "//div[@class='login-registration-card']//h1")
    WebElement titleOfPage;

    @FindBy(xpath = "//span[@class='navigator']")
    WebElement linkIfAlreadyRegistered;

    public void clickOnLinkIfAlreadyRegistered() {
 //       pause(1);
        linkIfAlreadyRegistered.click();
    }

    public boolean isSignUpPage() {
        return "title".equals(titleOfPage.getAttribute("class"))
                && "Registration".equals(titleOfPage.getText());
    }

    public void inputUserData(ArrayList<String> userData) {
        Iterator<WebElement> inputIterator = inputElements.iterator();
        Iterator<String> dataIterator = userData.iterator();
        while (inputIterator.hasNext() && dataIterator.hasNext()) {
            WebElement input = inputIterator.next();
            String fieldDataUser = dataIterator.next();
            input.sendKeys(fieldDataUser);
        }
    }

    public void setCheckBoxTermsOfUse() {
        labelCheckBox.click();
        WebElement inputCheckbox = inputElements.get(inputElements.size() - 1);
        String currentClass = inputCheckbox.getAttribute("class");
        if (!currentClass.contains("ng-valid") && currentClass.contains("ng-invalid")) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", inputCheckbox);
            System.out.println("label check box not took on");
        }
    }

    public void submitRegistration() throws NoSuchElementException {
        if (btnSubmit.isEnabled()) {
            btnSubmit.click();
        } else {
            String message = "\n ===== problem =====\n" +
                    "user data are not correct or is not set term of use check box" +
                    "\n ======  ======\n";
            throw new NoSuchElementException(message);
        }
    }

    public void userRegistration(UserDto user) {
        inputUserData(user.getUserData());
        setCheckBoxTermsOfUse();
        try {
            submitRegistration();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }


}


