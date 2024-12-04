package ilcarro_zlv.manager;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


@Getter
public class ApplManager {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//                        set option for driver wait for full load of page
    }

    @AfterMethod
    public void tearDown() {
       if (driver != null) {
            driver.quit();
        }
    }

}
