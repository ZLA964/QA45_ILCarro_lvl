package tests;

import ilcarro.manager.ApplicationManager;
import ilcarro.pages.ResultsPage;
import ilcarro.pages.SearchPage;
import ilcarro.utils.TestNGListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Listeners(TestNGListener.class)
public class SearchCarTests extends ApplicationManager {
    SearchPage searchPage;

    @Test
    public void searchCarPositiveTest(){
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("Haifa", "12/25/2024", "12/27/2024");
       Assert.assertTrue(new ResultsPage(getDriver()).isUrlResultsPresent());
    }

    @Test
    public void searchCarNegativeTest_wrongStartDate(){
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("Haifa", "12//2024", "12/27/2024");
        Assert.assertTrue(searchPage.isErrorMessage("Dates are required"));
    }

    @Test   // //div[@class='ng-star-inserted']
    public void searchCarNegativeTest_wrongLocation(){
        int plusDay = 3;
        int daysRent = 10;
        LocalDateTime from  = LocalDateTime.now().plusDays(plusDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String fromDate = from.format(formatter);
        String toDate = from.plusDays(daysRent).format(formatter);
        System.out.println(fromDate);
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("", fromDate, toDate);
        Assert.assertTrue(searchPage.isErrorMessage("City is required"));
    }

    @Test
    public void searchCarPositiveTestWithCalendar(){
        logger.info("test searchCarPositiveTestWithCalendar  with data --> " + "Haifa " + "dec/27/2024 " + "Aug/17/2025");
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWithCalendar("Haifa", "dec/27/2024", "Aug/17/2025");
        Assert.assertTrue(new ResultsPage(getDriver()).isUrlResultsPresent());
    }




}
