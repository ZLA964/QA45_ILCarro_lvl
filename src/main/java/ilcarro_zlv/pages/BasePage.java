package ilcarro_zlv.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wD) {
        driver = wD;
    }

//    public static void pause(int time) {
//        try {
//            Thread.sleep(time*1000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public boolean isTextInElementPresent(WebElement element, String text){
        return element.getText().contains(text);
    }

    public void pause(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restoring the interrupt flag
        }
    }

}