package lt.itacademy.pom.SchedulePagesTests;

import lt.itacademy.SchedulesPages.EditSchedulePage;
import lt.itacademy.SchedulesPages.SchedulePage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditSchedulePageTest extends BaseTest {

    MainPage mainPage;
    SchedulePage schedulePage;
    EditSchedulePage editSchedulePage;

    /**
     * Test Scenario: Check print schedule functionality
     */

    /**
     * User can print a schedule for a selected group test
     *
     * - open Tvarkarasciai page
     * - select a group and clicks 'view schedule' button
     * - click print button
     */

    @Test
    @Tag("regression")
    public void TestUserCanPrintSchedule(){
        mainPage = new MainPage(driver);
        schedulePage = new SchedulePage(driver);
        editSchedulePage = new EditSchedulePage(driver);

        mainPage.clickTvarkarasciaiItem();

        WaitUtils.waitForList(driver);
        String thirdGroupName = schedulePage.getThirdScheduleNameFromList();
        schedulePage.clickToSelect3rdScheduleFromList();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("table:first-child > tbody > tr > td.six.wide"), thirdGroupName));
        String displayedGroupNameInViewSchedulePage = editSchedulePage.getScheduleNameInViewSchedulePage();
        Assertions.assertEquals(thirdGroupName, displayedGroupNameInViewSchedulePage, "Group names do not match");

        Boolean printButtonIsClickable = editSchedulePage.isPrintButtonInViewScheduleWindowClickable();
        Assertions.assertTrue(printButtonIsClickable, "Print button is disabled");

    }
}
