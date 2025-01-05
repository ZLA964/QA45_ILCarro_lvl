package ilcarro.pages;

import ilcarro.manager.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ilcarro.utils.TakeScreenShot.takeScreenShort;

public class BasePage {
    static WebDriver driver;
    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

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

    //div[@class="cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing"]


    public boolean isTextInElementPresent(WebElement element, String text) {
        if (element == null) {
            return false;
        } else {
            if (element.getText() == null) {
                return false;
            }
        }
        logger.info("== checking Is '"+text+ "' present in\n\t\telement " +element.toString()+
                "\n\t\tsee created screenshot " +
                takeScreenShort((TakesScreenshot) driver));
        return element.getText().contains(text);
    }

    public void clickWait(WebElement element, int time) {
        try {
            long startTime = System.currentTimeMillis();
            new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(element)).click();
            long waitTime = System.currentTimeMillis() - startTime;
            System.out.println("clickWait " + element.getText() + " waited -> " + waitTime + " ms");
        } catch (Exception e) {
            logger.info("ClilWait created exception on " + element.toString() +
                    "\n\t\t created screenshot " +
                    takeScreenShort((TakesScreenshot) driver));
        }
    }

    // wait new Element on Page
    public void waitNewElementOnPage(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public boolean waitElementIsNotOnPage(WebElement element, int timeoutInSeconds) {
        return new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean validateElemenIsNotPresent(String locator) {
        try {
            long startTime = System.currentTimeMillis();
            new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOfElementLocated
                    (By.xpath("//div[@class='cdk-overlay-backdrop cdk-overlay-dark-backdrop cdk-overlay-backdrop-showing']")));
            long waitTime = System.currentTimeMillis() - startTime;
            System.out.println("5 seconds pass ? -> " + waitTime + " ms");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateUrl(String url, int time) {
        return new WebDriverWait(driver, time).until(ExpectedConditions.urlContains(url));
    }

    public boolean isWebElementDisabled(WebElement element, int time) {
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

    public boolean validateElementIsNotPresentV2(String locator) {
        try {
            long startTime = System.currentTimeMillis();

            new WebDriverWait(driver, 5)
                    .until(driver -> {
                        boolean isInvisible = ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)).apply(driver);
                        System.out.println("Checked invisibility: " + isInvisible);
                        return isInvisible;
                    });

            long waitTime = System.currentTimeMillis() - startTime;
            System.out.println("Time waited: " + waitTime + " ms");
            return true;
        } catch (Exception e) {
            System.out.println("Element is still visible or another exception occurred.");
            return false;
        }
    }

    public boolean isElementPresentDOM(String locator, int time) {
        try {
            long startTime = System.currentTimeMillis();
            new WebDriverWait(driver, time)
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
            long waitTime = System.currentTimeMillis() - startTime;
            System.out.println("isElementPresentDOM " + locator + " waited -> " + waitTime + " ms");
            return true;
        } catch (Exception e) {
            System.out.println("isElementPresentDOM created exception");
            return false;
        }
    }

}
