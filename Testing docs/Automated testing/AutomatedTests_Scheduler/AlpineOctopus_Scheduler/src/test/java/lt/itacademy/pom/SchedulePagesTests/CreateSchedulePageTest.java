package lt.itacademy.pom.SchedulePagesTests;

import lt.itacademy.GroupPages.CreateGroupPage;
import lt.itacademy.SchedulesPages.CreateSchedulePage;
import lt.itacademy.SchedulesPages.SchedulePage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CreateSchedulePageTest extends BaseTest {

    MainPage mainPage;
    CreateSchedulePage createSchedulePage;
    CreateGroupPage createGroupPage;

    /**
     * Test Scenario: Check new schedule creation functionality
     */

    /**
     * User is redirected from 'create schedule' page to 'create group' page if needed group hasn't been created
     * - open Tvarkarasciai page
     * - click 'Kurti nauja' button
     * - click 'Sukurti grupe' button (+)
     * - check if 'Create group' page opens
     *
     */
    @Test
    @Tag("smoke")
    public void TestUserIsRedirectedToCreateGroupPage(){
        mainPage = new MainPage(driver);
        createSchedulePage = new CreateSchedulePage(driver);
        createGroupPage = new CreateGroupPage(driver);

        mainPage.clickTvarkarasciaiItem();
        createSchedulePage.clickKurtiNaujaSchedule();
        createSchedulePage.clickKurtiNaujaGrupeInCreateScheduleWindow();
        String expectedGrupesTitle = "\"Teams\" grupės pavadinimas";
        String displayedGrupesTitle = createGroupPage.getPageTitleInCreateGroupWindow();
        Assertions.assertEquals(expectedGrupesTitle, displayedGrupesTitle, "Titles do not match");

    }

}
