package lt.itacademy.RoomPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoomPage extends AbstactPage {

    @FindBy(css = "tbody > tr:first-child > td:first-child")
    private WebElement firstRoomInList;

    @FindBy(css = "tbody > tr:nth-child(4) > td:first-child")
    private WebElement fourthRoomInList;

    @FindBy(css = "tbody > tr:nth-child(4) > td:nth-child(3) > button")
    private WebElement fourthRoomArchiveButton;

    public RoomPage(WebDriver driver) {
        super(driver);
    }

    public String getNameForFirstRoomInList(){
        return firstRoomInList.getText();
    }

    public void clickFourthRoomArchiveButton(){
        fourthRoomArchiveButton.click();
    }

    public String getFourthRoomInListName(){
        return fourthRoomInList.getText();
    }
}
