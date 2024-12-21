package ilcarro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wD) {
        driver = wD;
    }

    public static void pause(int time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FindBy(xpath = "//h2[@class='message']")
    WebElement popUpMessage;

    public boolean isTextInElementPresent(WebElement element, String text) {
        return element.getText().contains(text);
    }

    public void clickWait(WebElement element, int time) {
        new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // wait new Element on Page
    public void waitNewElementOnPage(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public boolean validateUrl(String url, int time) {
        return new WebDriverWait(driver, time).until(ExpectedConditions.urlContains(url));
    }

    public boolean isWebElementDisabled( WebElement element, int time) {
        if (!element.isDisplayed()) {                         // Check that the element is visible
            throw new IllegalStateException("Element is not visible on the page.");
        }
        if (element.isEnabled()) {                            // Check that the element is enabled
            return false;                                     // Element is active, not disabled
        }
        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            wait.until((ExpectedCondition<Boolean>) driver -> !element.getAttribute("disabled").equals("true"));
        } catch (Exception e) {
            return true;                     // If the attribute does not disappear, return true
        }
        return false;                        // If the attribute disappears, the element is active
    }

}
