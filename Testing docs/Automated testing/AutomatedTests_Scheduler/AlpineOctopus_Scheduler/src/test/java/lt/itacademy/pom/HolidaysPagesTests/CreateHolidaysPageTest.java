package lt.itacademy.pom.HolidaysPagesTests;

import lt.itacademy.HolidaysPages.CreateHolidaysPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CreateHolidaysPageTest extends BaseTest {

    MainPage mainPage;
    CreateHolidaysPage createHolidaysPage;

    /**
     * User cannot create new holidays with empty name
     * - open Atostogos page
     * - click Kurti nauja for new holiday creation
     * - click new holiday name box
     * - click 'Taip' for repeated holiday
     * - check if warning message for empty name is displayed
     * - check if Sukurti button is disabled
     */


    @Test
    @Tag("regression")
    public void TestUserCannotCreateHolidaysWithEmptyName(){
        mainPage = new MainPage(driver);
        createHolidaysPage = new CreateHolidaysPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickAtostogosTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        createHolidaysPage.clickNewHolidayNameBox();
        createHolidaysPage.clickTaipButtonForRepeatedHoliday();

        String expectedWaringMsg = "Pavadinimas negali būti tuščias!";
        String displayedWarningMsg = createHolidaysPage.getTextForWarningMsgForEmptyHolidayName();

        Assertions.assertEquals(expectedWaringMsg, displayedWarningMsg, "Warning messages do not match");
        Assertions.assertFalse(createHolidaysPage.isSukurtiHolidaysButtonEnabled(), "Sukurti button is enabled");
    }


}
