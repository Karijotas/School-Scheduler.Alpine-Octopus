package lt.itacademy.pom.RoomPagesTest;

import lt.itacademy.RoomPages.ArchiveRoomPage;
import lt.itacademy.RoomPages.RoomPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ArchiveRoomPageTest extends BaseTest {

    MainPage mainPage;
    RoomPage roomPage;
    ArchiveRoomPage archiveRoomPage;

    /**
     * Test Scenario: Check room archive functionality
     */

    /**
     * User can archive room
     * - open Kabinetai page
     * - click archive button for fourth room
     * - click Taip
     * - open Kabinetu archyvas page and check if archived room is in the beginning of the archived rooms list
     */

    @Test
    @Tag("regression")
    public void TestUserCanArchiveRoom(){
        mainPage = new MainPage(driver);
        roomPage = new RoomPage(driver);
        archiveRoomPage = new ArchiveRoomPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickKabinetaiTabItem();
        WaitUtils.waitForList(driver);
        String fourthRoomInList = roomPage.getFourthRoomInListName();
        roomPage.clickFourthRoomArchiveButton();
        archiveRoomPage.clickTaipButtonToArchiveRoom();

        mainPage.clickArchiveButton();
        archiveRoomPage.clickKabinetuArchyvasTab();
        WaitUtils.waitForList(driver);
        String archivedRoomName = archiveRoomPage.getFirstRoomNameInArchiveList();

        Assertions.assertEquals(fourthRoomInList, archivedRoomName, "Room names do not match");
    }


    /**
     * User can restore archived room
     * - open Kabinetu archyvas page
     * - click restore button for first room in archived rooms list
     * - open rooms list
     * - check if the first room name in the list matches the restored rooms name
     */

    @Test
    @Tag("regression")
    public void TestUserCanRestoreArchivedRoom(){
        mainPage = new MainPage(driver);
        roomPage = new RoomPage(driver);
        archiveRoomPage = new ArchiveRoomPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickArchiveButton();
        archiveRoomPage.clickKabinetuArchyvasTab();
        WaitUtils.waitForList(driver);
        String firstArchivedRoomName = archiveRoomPage.getFirstRoomNameInArchiveList();
        archiveRoomPage.clickToRestoreFirstRoomButton();

        mainPage.clickKabinetaiTabItem();
        WaitUtils.waitForList(driver);
        String restoredRoomName = roomPage.getNameForFirstRoomInList();

        Assertions.assertEquals(firstArchivedRoomName, restoredRoomName, "Names do not match");
    }
}
