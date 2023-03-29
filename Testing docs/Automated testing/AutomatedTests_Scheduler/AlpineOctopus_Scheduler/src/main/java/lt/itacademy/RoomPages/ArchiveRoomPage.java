package lt.itacademy.RoomPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArchiveRoomPage extends AbstactPage {

    @FindBy(css = "body > div.modals:last-child > div > div.actions > button.ui.primary.button")
    private WebElement taipButtonToArchiveRoom;

    @FindBy(xpath = "//a[text()='Kabinetų archyvas']")
    private WebElement kabinetuArchyvasTab;

    @FindBy(css = "tbody > tr:first-child")
    private WebElement firstRoomInArchivedList;

    @FindBy(css = "table > tbody > tr:first-child button")
    private WebElement restoreFirstRoomButton;

    public ArchiveRoomPage(WebDriver driver) {
        super(driver);
    }

    public void clickTaipButtonToArchiveRoom() {
        taipButtonToArchiveRoom.click();
    }

    public void clickKabinetuArchyvasTab(){
        kabinetuArchyvasTab.click();
    }

    public String getFirstRoomNameInArchiveList(){
        return firstRoomInArchivedList.getText();
    }

    public void clickToRestoreFirstRoomButton(){
        restoreFirstRoomButton.click();
    }
}
