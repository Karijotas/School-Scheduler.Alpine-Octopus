package lt.itacademy.RoomPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateRoomPage extends AbstactPage {

    @FindBy(css = "input[placeholder=\"Klasės pavadinimas\"]")
    private WebElement kabinetoPavadinimasBox;

    @FindBy(css = "input[placeholder=\"Pastatas\"]")
    private WebElement pastatasBox;

    @FindBy(xpath = "//button[text()=\"Sukurti\"]")
    private WebElement sukurtiRoomButton;
    public CreateRoomPage(WebDriver driver) {
        super(driver);
    }


    public void sendTextToNewRoomNameBox(String newRoomName){
        kabinetoPavadinimasBox.sendKeys(newRoomName);
    }
    public void sendTextToPastatasBox(String building){ pastatasBox.sendKeys(building);}

    public void clickSukurtiButton(){
        sukurtiRoomButton.click();
    }
}
