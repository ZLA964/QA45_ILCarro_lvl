package tests;

import ilcarro.manager.ApplicationManager;
import ilcarro.pages.ResultsPage;
import ilcarro.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchCarTests extends ApplicationManager {
    SearchPage searchPage;

    @Test
    public void searhCarPositiveTest(){
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("Haifa", "12/25/2024", "12/27/2024");
       Assert.assertTrue(new ResultsPage(getDriver()).isUrlResultsPresent());
    }
}
