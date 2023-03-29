package lt.itacademy.pom.GroupPagesTests;

import lt.itacademy.GroupPages.CreateGroupPage;
import lt.itacademy.GroupPages.GroupsPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class CreateGroupPageTest extends BaseTest {

    private String studentCount = "30";
    MainPage mainPage;
    GroupsPage groupsPage;
    CreateGroupPage createGroupPage;



    /**
     * Test Scenario: Check new group creation functionality
     */

    /**
     * User can create new group
     * - open Sukurti nauja grupe page
     * - check if Sukurti button is disabled
     * - write group name
     * - select year
     * - write the count of students
     * - select program
     * - select shift
     * - click Sukurti
     */

    @ParameterizedTest
    @Tag("smoke")
    @CsvFileSource(resources = "/groupsToCreate.txt")
    public void TestUserCreatesNewGroup(String groupToCreate){
        mainPage = new MainPage(driver);
        groupsPage = new GroupsPage(driver);
        createGroupPage = new CreateGroupPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickGrupesTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();

        String sentGroupName = groupToCreate + RandomUtils.generateTimeStamp();
        createGroupPage.sendNewGroupName(sentGroupName);

        createGroupPage.clickSelectYearDropDown();
        createGroupPage.sendTextToStudentuSkaiciusBox(studentCount);

        createGroupPage.clickProgramaDropdown();
        createGroupPage.clickProgram();

        createGroupPage.clickPamainaDropdown();
        createGroupPage.clickShift();

        createGroupPage.clickSukurtiPamainaButton();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("table > tbody > tr:first-child > td:first-child"), sentGroupName));

        String displayedFirstGroupName = groupsPage.getFirstGroupInTheListName();
        Assertions.assertEquals(sentGroupName, displayedFirstGroupName, "Group names do not match");
    }

    /**
     * User cannot create new group when group name is empty
     * - open Sukurti nauja grupe page
     * - check if Sukurti button is disabled
     */

    @Test
    @Tag("smoke")
    public void TestUserCannotCreatesNewGroupWhenNameIsEmpty() {
        mainPage = new MainPage(driver);
        groupsPage = new GroupsPage(driver);
        createGroupPage = new CreateGroupPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickProgramosTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();

        Boolean isSukurtiButtonEnabled = createGroupPage.isSukurtiButtonEnabled();
        Assertions.assertFalse(isSukurtiButtonEnabled, "Sukurti Button is enabled");

    }




}
