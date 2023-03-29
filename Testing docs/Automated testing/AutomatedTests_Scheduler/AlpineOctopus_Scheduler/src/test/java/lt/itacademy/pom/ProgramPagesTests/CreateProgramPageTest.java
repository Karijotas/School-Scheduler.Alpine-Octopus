package lt.itacademy.pom.ProgramPagesTests;

import lt.itacademy.ProgramPages.CreateProgramPage;
import lt.itacademy.ProgramPages.EditProgramPage;
import lt.itacademy.ProgramPages.ProgramPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Date;

public class CreateProgramPageTest extends BaseTest {

    MainPage mainPage;
    ProgramPage programPage;
    CreateProgramPage createProgramPage;
    EditProgramPage editProgramPage;
    /**
     * Test Scenario: Check program creation functionality
     */


    /**
     * User can create new program
     * - open Programos page
     * - open Kurti nauja programa page
     * - write new program name
     * - click Sukurti button
     * - go to main programs list
     * - check that the first program's name in the list match new program's name
     *
     * @param programToCreate
     */

    @ParameterizedTest
    @Tag("smoke")
    @CsvFileSource(resources = "/programsToCreate.txt")
    public void TestUserCanCreateNewProgram(String programToCreate){
        mainPage = new MainPage(driver);
        programPage = new ProgramPage(driver);
        createProgramPage = new CreateProgramPage(driver);
        editProgramPage = new EditProgramPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickProgramosTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        String newProgramName = programToCreate + RandomUtils.generateTimeStamp();
        createProgramPage.sendTextToProgramosPavadinimas(newProgramName);
        createProgramPage.clickSukurtiProgramButton();
        editProgramPage.clickAtgalToProgramListButton();

        WaitUtils.waitForList(driver);
        String displayedProgramName = programPage.getFirstProgramName();
        Assertions.assertEquals(newProgramName, displayedProgramName, "Program names do not match");

    }


    /**
     * User cannot create a new program if name field is empty
     * - open Programos page, click Sukurti nauja programa button
     * - check if Sukurti Button is disabled
     * - check if warning message for empty program name appears
     */

    @Test
    @Tag("regression")
    public void TestUserCannotCreateNewProgramWithEmptyName(){
        mainPage = new MainPage(driver);
        programPage = new ProgramPage(driver);
        createProgramPage = new CreateProgramPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickProgramosTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();

        boolean isSukurtiButtonEnabled = createProgramPage.isSukurtiButtonEnabled();
        Assertions.assertFalse(isSukurtiButtonEnabled, "The button is enabled");

        createProgramPage.clickProgramNameField();
        createProgramPage.clickProgramDescriptionField();
        String expectedWarningMessage = "Negali būti tuščias!";
        String displayedWarningMessage = createProgramPage.getWarningTextForEmptyProgramName();
        Assertions.assertEquals(expectedWarningMessage, displayedWarningMessage, "Messages do not match");
    }


}
