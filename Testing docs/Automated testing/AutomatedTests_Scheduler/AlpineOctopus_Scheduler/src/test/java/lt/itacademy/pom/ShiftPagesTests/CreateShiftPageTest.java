package lt.itacademy.pom.ShiftPagesTests;

import lt.itacademy.ShiftPages.CreateShiftPage;
import lt.itacademy.ShiftPages.ShiftPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


public class CreateShiftPageTest extends BaseTest {

    MainPage mainPage;
    ShiftPage shiftPage;
    CreateShiftPage createShiftPage;

    /**
     * Test Scenario: Check new shift creation functionality
     */


    /**
     * User can create new shift
     * - open Pamainos page
     * - click 'Sukurti nauja pamaina' button
     * - check if 'Sukurti' button is disabled when shift name box is empty
     * - enter text to mandatory fields
     * - click 'Sukurti'
     * - check that new shift appears in the beginning of the shifts list
     *
     * @param shiftFromFile
     */
    @ParameterizedTest
    @Tag("smoke")
    @CsvFileSource(resources = "/shiftsToCreate.txt")
    public void TestUserCanCreateNewShift(String shiftFromFile){
        mainPage = new MainPage(driver);
        shiftPage = new ShiftPage(driver);
        createShiftPage = new CreateShiftPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickPamainosTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();

        Boolean isSukurtiButtonDisabled = createShiftPage.isSukurtiButtonDisabled();
        Assertions.assertFalse(isSukurtiButtonDisabled, "Sukurti button is disabled");

        String sentShiftName = shiftFromFile + RandomUtils.generateTimeStamp();
        createShiftPage.sendTextPamainosPavadinimasBox(sentShiftName);
        createShiftPage.sendTextPamokosNuoBox("3");
        createShiftPage.sendTextPamokosIkiBox("7");
        createShiftPage.clickSukurtiPamainaButton();

        String expectedShiftName = sentShiftName;
        WaitUtils.waitForList(driver);
        String displayedShiftName = shiftPage.getNameFirstShiftInTheList();
        Assertions.assertEquals(expectedShiftName, displayedShiftName, "Shift names do not match");

    }


    /**
     * User cancels new shift creation
     * - open Pamainos page
     * - get the name of the first shift in the list
     * - click "Kurti nauja pamaina"
     * - fill in the information for new shift
     * - click "Grizti atgal" button
     * - the first shift still appears the first, no new shift
     *
     */
    @Test
    @Tag("regression")
    public void TestUserDoesNotWantToCreateNewShift(){
        mainPage = new MainPage(driver);
        shiftPage = new ShiftPage(driver);
        createShiftPage = new CreateShiftPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickPamainosTabItem();

        WaitUtils.waitForList(driver);

        String firstShiftInTheList = shiftPage.getNameFirstShiftInTheList();

        mainPage.clickKurtiNaujaKlasifikatoriu();

        Boolean isSukurtiButtonDisabled = createShiftPage.isSukurtiButtonDisabled();
        Assertions.assertFalse(isSukurtiButtonDisabled, "Sukurti button is disabled");

        String sentShiftName = "Pamaina" + RandomUtils.generateTimeStamp();
        createShiftPage.sendTextPamainosPavadinimasBox(sentShiftName);
        createShiftPage.sendTextPamokosNuoBox("3");
        createShiftPage.sendTextPamokosIkiBox("7");
        createShiftPage.clickAtgalToShiftsListButton();

        String firstShiftInTheListAfterCancelShiftCreation = shiftPage.getNameFirstShiftInTheList();
        Assertions.assertEquals(firstShiftInTheList, firstShiftInTheListAfterCancelShiftCreation, "Shift names do not match");
    }


}
