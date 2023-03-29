package lt.itacademy.pom.HolidaysPagesTests;

import lt.itacademy.HolidaysPages.HolidaysPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class HolidaysPageTest extends BaseTest {
    MainPage mainPage;
    HolidaysPage holidaysPage;

    /**
     * Test Scenario: Check holidays list functionality
     */

    /**
     * User can filter holidays list by name
     * - open Atostogos page
     * - write text to Filtruoti pagal pavadinima box
     * - check if the filtered name matches the filtering text
     *
     * @param holidayToFilter
     */

    @ParameterizedTest
    @Tag("regression")
    @CsvFileSource(resources = "/holidaysToFilter.txt")
    public void TestUserCanFilterHolidaysByName(String holidayToFilter){
        mainPage = new MainPage(driver);
        holidaysPage = new HolidaysPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickAtostogosTabItem();
        holidaysPage.sendTextToFilterHolidayByName(holidayToFilter);
        WaitUtils.waitForList(driver);
        String filteredHolidaysName = holidaysPage.getNameForFirstHolidayInList();

        Assertions.assertEquals(holidayToFilter, filteredHolidaysName, "Holidays name do not match");
    }

}
