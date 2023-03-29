package lt.itacademy.pom.TeacherPagesTests;

import lt.itacademy.TeacherPages.CreateTeacherPage;
import lt.itacademy.TeacherPages.EditTeachersPage;
import lt.itacademy.TeacherPages.TeacherPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateTeacherPageTest extends BaseTest {

    private String teacherName = "Vytis Vytautas";
    MainPage mainPage;
    CreateTeacherPage createTeacherPage;
    EditTeachersPage editTeacherPage;
    TeacherPage teacherPage;

    /**
     * Test Scenario: Check teacher creation functionality
     */

    /**
     * User can create new teacher
     * - open Mokytojai page
     * - click Kurti nauja mokytoja button
     * - write teacher's name
     * - write teacher's teams name
     * - write teacher's email
     * - write teacher's phone number
     * - click Sukurti button
     * - click Atgal button to go to teachers list
     * - check if the first teacher's name in the list matches created teachers name
     */

    @Test
    @Tag("smoke")
    public void TestUserCanCreateNewTeacher(){
        mainPage = new MainPage(driver);
        createTeacherPage = new CreateTeacherPage(driver);
        editTeacherPage = new EditTeachersPage(driver);
        teacherPage = new TeacherPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickMokytojaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        String newTeacherName = RandomUtils.getRandomName();
        createTeacherPage.sendTextToVardasPavardeForNewTeacher(newTeacherName);
        String newTeacherTeamsName = "vytis" + RandomUtils.generateTimeStamp() + "@vytautas.lt";
        createTeacherPage.sendTextToTeamsNameForNewTeacher(newTeacherTeamsName);
        createTeacherPage.sendTextToElPastasForNewTeacher(newTeacherTeamsName);
        String newTeacherPhoneNo = "861212345";
        createTeacherPage.sendTextToTelefonoNrForNewTeacher(newTeacherPhoneNo);
        createTeacherPage.clickSukurtiMokytojaButton();
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.elementToBeClickable(editTeacherPage.atgalToTeacherListButton));
        editTeacherPage.clickAtgalToTeacherListButton();
        WaitUtils.waitForList(driver);
        String displayedTeacherName = teacherPage.getFirstTeacherNameInList();

        Assertions.assertEquals(newTeacherName, displayedTeacherName, "Teacher named do not match");
    }


    /**
     * User cannot create new teacher with empty teacher name
     * - open Mokytojai page
     * - click Kurti nauja mokytoja button
     * - click on name box
     * - click on teams name box
     * - check if warning message for empty teacher name is displayed
     * - check if Sukurti button is disabled when teacher name is empty
     */

    @Test
    @Tag("regression")
    public void TestUserCannotCreateTeacherWithEmptyName(){
        mainPage = new MainPage(driver);
        createTeacherPage = new CreateTeacherPage(driver);
        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickMokytojaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        createTeacherPage.clickVardasPavardeForNewTeacherBox();
        createTeacherPage.clickTeamsNameForNewTeacher();
        String expectedWarningMsgForEmptyTeacherName = "Negali būti tuščias!";
        String displayedWarningMsgForEmptyTeacherName = createTeacherPage.getWarningMsgForEmptyTeacherName();

        Assertions.assertEquals(expectedWarningMsgForEmptyTeacherName, displayedWarningMsgForEmptyTeacherName, "Warning messages do not match");
        Assertions.assertFalse(createTeacherPage.isSukurtiButtonEnabled(), "Sukurti button is enabled");
    }
}
