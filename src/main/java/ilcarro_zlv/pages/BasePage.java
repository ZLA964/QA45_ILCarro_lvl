package ilcarro_zlv.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wD) {
        driver = wD;
    }


    public void pause(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restoring the interrupt flag
        }
    }

    // wait popUp byXpath
    public void waitForPopup(String xpath, int timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    // wait popUp Element
    public void waitForPopup(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isTextInElementPresent(WebElement element, String text) {
        return element.getText().contains(text);
    }
}


//import org.openqa.selenium.WebElement;

//    public static void pause(int time) {
//        try {
//            Thread.sleep(time*1000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public boolean isTextInElementPresent(WebElement element, String text){
//        return element.getText().contains(text);
//    }
