package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wD) {
        driver = wD;
    }

    public static void pause(int time) {
        try {
            Thread.sleep(time*1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public void pause(int timeInSeconds) {
//        try {
//            Thread.sleep(timeInSeconds * 1000L);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // Restoring the interrupt flag
//        }
//    }

}
