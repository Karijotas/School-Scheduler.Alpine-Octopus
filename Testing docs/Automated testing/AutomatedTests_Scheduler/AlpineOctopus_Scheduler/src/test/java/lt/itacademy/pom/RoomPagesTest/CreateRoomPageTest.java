package lt.itacademy.pom.RoomPagesTest;

import lt.itacademy.RoomPages.CreateRoomPage;
import lt.itacademy.RoomPages.RoomPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CreateRoomPageTest extends BaseTest {

    MainPage mainPage;
    CreateRoomPage createRoomPage;
    RoomPage roomPage;

    /**
     * Test Scenario: Check new room's creation functionality
     */

    /**
     * User can create new room
     * - open Kabinetai page
     * - click Kurti nauja kabineta button
     * - write new room name
     * - write building
     * - click Sukurti button
     * - check if new room is shown in the beginning of the rooms list
     *
     * @param roomToCreate
     */

    @ParameterizedTest
    @Tag("smoke")
    @CsvFileSource(resources = "/roomsToCreate.txt")
    public void TestUserCanCreateNewRoom(String roomToCreate){
        mainPage = new MainPage(driver);
        createRoomPage = new CreateRoomPage(driver);
        roomPage = new RoomPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickKabinetaiTabItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();

        String newRoomName = roomToCreate + RandomUtils.generateTimeStamp();
        createRoomPage.sendTextToNewRoomNameBox(newRoomName);
        createRoomPage.sendTextToPastatasBox("Trinapolio g. 33");
        createRoomPage.clickSukurtiButton();
        WaitUtils.waitForList(driver);
        String displayedRoomName = roomPage.getNameForFirstRoomInList();
        Assertions.assertEquals(newRoomName, displayedRoomName, "Room names do not match");

    }
}
