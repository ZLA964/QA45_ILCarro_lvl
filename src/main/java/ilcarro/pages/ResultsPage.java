package ilcarro.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ResultsPage extends BasePage{

    public ResultsPage(WebDriver webDrv){
        setDriver(webDrv);
        PageFactory.initElements(
                new AjaxElementLocatorFactory(driver,10), this);
    }

    public boolean isUrlResultsPresent(){
        return validateUrl("results", 5);
    }
}
