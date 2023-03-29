package lt.itacademy.pom.ShiftPagesTests;

import lt.itacademy.ShiftPages.ShiftPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ShiftPageTest extends BaseTest {
    MainPage mainPage;
    ShiftPage shiftPage;

    /**
     * Test Scenario: Check filtering shift by name functionality
     */

    /**
     * User can filter shift by name
     * - open Pamainos page
     * - write text 'rytas' to filter box
     * - check that more than 0 shifts were found
     */


    @Test
    @Tag("regression")
    public void TestUserCanFilterShiftByName(){
        mainPage = new MainPage(driver);
        shiftPage = new ShiftPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickPamainosTabItem();
        String shiftToFilter = "rytas";
        shiftPage.sendTextToShiftFilter(shiftToFilter);
        WaitUtils.waitForList(driver);
        int numberOfFilteredShifts = shiftPage.filteredShifts().size();

        Assertions.assertNotEquals(0, numberOfFilteredShifts, "No shifts were found");

    }
}
