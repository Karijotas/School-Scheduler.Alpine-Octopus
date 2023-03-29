package lt.itacademy.pom.SubjectPagesTests;

import jdk.jfr.Category;
import lt.itacademy.SubjectPages.CreateSubjectPage;
import lt.itacademy.SubjectPages.EditSubjectPage;
import lt.itacademy.SubjectPages.SubjectsPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CreateSubjectPageTest extends BaseTest {

    private String subjectName = "Matematika";
    private String subjectDescription = "Matematikos pagrindai";
    private String subjectNameTooShort = "A";
    private String subjectNameTooLong = "Journalism in the Media and Communications Industry Journalism in the Media and Communications Industry";

    MainPage mainPage;
    CreateSubjectPage createSubjectPage;
    EditSubjectPage editSubjectPage;
    SubjectsPage subjectsPage;

    /**
     * Test Scenario: Check subject creation functionality
     */

    /**
     * User can create new subject
     * - open Dalykai page
     * - click 'Sukurti nauja dalyka' button
     * - fill in all information
     * - click 'Sukurti' button
     * - check that the new subject appears in the beginning of the subject list
     */
    @Test
    @Tag("smoke")

    public void CreateNewSubjectTest() {
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        createSubjectPage = new CreateSubjectPage(driver);
        editSubjectPage = new EditSubjectPage(driver);


        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        String newSubjectName = subjectName + RandomUtils.generateTimeStamp();
        createSubjectPage.sendTextToDalykoPavadinimas(newSubjectName);
        createSubjectPage.sendTextToDalykoAprasymas(subjectDescription);
        createSubjectPage.clickSukurtiDalykaButton();

        WaitUtils.waitForList(driver);
        editSubjectPage.clickAtgalToSubjectListButton();

        WaitUtils.waitForList(driver);
        String actualDisplayedSubjectName = subjectsPage.getFirstSubjectText();
        Assertions.assertTrue(actualDisplayedSubjectName.startsWith("Matematika"), "Subject names do not match");
    }


    /**
     * User cannot create a new subject if the name field is empty
     * - open Dalykai page
     * - click 'Sukurti nauja dalyka' button
     * - check the warning message appears that the name cannot be empty
     * - check the button 'Sukurti' is disabled
     */
    @Test
    @Tag("regression")
    public void UnableToCreateNewSubjectWithEmptySubjectName(){
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        createSubjectPage = new CreateSubjectPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        createSubjectPage.clickDalykoPavadinimasBox();
        createSubjectPage.clickDalykoAprasymasBox();

        String expectedErrorMsgForBlankSubjectName = "Negali būti tuščias!";
        String displayedErrorMessageForBlankSubjectName = createSubjectPage.getErrorMsgTextInCreateSubject();

        WebElement sukurtiButton = driver.findElement(By.cssSelector("button[id='details']"));

        Assertions.assertEquals(expectedErrorMsgForBlankSubjectName, displayedErrorMessageForBlankSubjectName, "Error messages do not match");
        Assertions.assertFalse(sukurtiButton.isEnabled(), "Button 'Sukurti for a new subject is enabled");
    }


    /**
     * User cannot create a new subject if the name is too short
     * - open Dalykai page
     * - click 'Sukurti nauja dalyka' button
     * - enter one symbol to the name box
     * - check the warning message appears that the name mus be between 2 and 100 symbols
     * - check the button 'Sukurti' is disabled
     *
     */

    @Test
    @Tag("regression")
    public void UnableToCreateNewSubjectWithTooShortSubjectName() {
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        createSubjectPage = new CreateSubjectPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        createSubjectPage.clickDalykoPavadinimasBox();
        createSubjectPage.sendTextToDalykoPavadinimas(subjectNameTooShort);
        createSubjectPage.clickDalykoAprasymasBox();

        String expectedErrorMsgForTooShortSubjectName = "Įveskite nuo 2 iki 100 simbolių!";
        String displayedErrorMessageForTooShortSubjectName = createSubjectPage.getErrorMsgTextInCreateSubject();

        WebElement sukurtiButton = driver.findElement(By.cssSelector("button[id='details']"));
        Assertions.assertEquals(expectedErrorMsgForTooShortSubjectName, displayedErrorMessageForTooShortSubjectName, "Error messages do not match");
        Assertions.assertFalse(sukurtiButton.isEnabled(), "Button 'Sukurti for a new subject is enabled");
    }



    /**
     * User cannot create a new subject if the name is too long
     * - open Dalykai page
     * - click 'Sukurti nauja dalyka' button
     * - enter 101 symbols to the name box
     * - check the warning message appears that the name mus be between 2 and 100 symbols
     * - check the button 'Sukurti' is disabled
     *
     */


    @Test
    @Tag("regression")
    public void UnableToCreateNewSubjectWithTooLongSubjectName() {
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        createSubjectPage = new CreateSubjectPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        createSubjectPage.clickDalykoPavadinimasBox();
        createSubjectPage.sendTextToDalykoPavadinimas(subjectNameTooLong);
        createSubjectPage.clickDalykoAprasymasBox();

        String expectedErrorMsgForTooLongSubjectName = "Įveskite nuo 2 iki 100 simbolių!";
        String displayedErrorMessageForTooLongSubjectName = createSubjectPage.getErrorMsgTextInCreateSubject();

        WebElement sukurtiButton = driver.findElement(By.cssSelector("button[id='details']"));
        Assertions.assertEquals(expectedErrorMsgForTooLongSubjectName, displayedErrorMessageForTooLongSubjectName, "Error messages do not match");
        Assertions.assertFalse(sukurtiButton.isEnabled(), "Button 'Sukurti for a new subject is enabled");
    }
  }
