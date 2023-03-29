package lt.itacademy.pom.SchedulePagesTests;

import lt.itacademy.SchedulesPages.SchedulePage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SchedulePageTest extends BaseTest {

    MainPage mainPage;
    SchedulePage schedulePage;

    /**
     * Test Scenario: Check filtering functionality for group list
     */

    /**
     * User can filter schedules by name
     * - open Tvarkarasciai page
     * - enter text into "Filtruoti pagal pavadinima" box
     * - check only correct schedules are shown
     *
     * @param scheduleFromFile
     */
    @ParameterizedTest
    @Tag("regression")
    @CsvFileSource(resources = "/schedulesToFilter.txt")
    public void TestUserCanFilterSchedulesByName(String scheduleFromFile){
        mainPage = new MainPage(driver);
        schedulePage = new SchedulePage(driver);

        mainPage.clickTvarkarasciaiItem();

        schedulePage.sendTextToFilterScheduleByNameBox(scheduleFromFile);

        int numberOfFoundSchedules = schedulePage.searchedSchedules().size();
        Assertions.assertNotEquals(0, numberOfFoundSchedules, "No schedules were found for " + scheduleFromFile);

    }
}
