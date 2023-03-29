package lt.itacademy.ShiftPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateShiftPage extends AbstactPage {

    @FindBy(css = "input[placeholder=\"Pamainos pavadinimas\"]")
    private WebElement pamainosPavadinimasBox;

    @FindBy(css = "input[placeholder=\"Pamokos nuo:\"]")
    private WebElement pamokosNuoBox;

    @FindBy(css = "input[placeholder=\"Pamokos iki:\"]")
    private WebElement pamokosIkiBox;

    @FindBy(xpath = "//button[text()=\"Sukurti\"]")
    private WebElement sukurtiPamainaButton;

    @FindBy(xpath = "//a[text()=\"Atgal\"]")
    private WebElement atgalToShiftsListButton;




    public CreateShiftPage(WebDriver driver) {
        super(driver);
    }

    public void sendTextPamainosPavadinimasBox(String shiftFromFile){
        pamainosPavadinimasBox.sendKeys(shiftFromFile);
    }

    public void sendTextPamokosNuoBox(String pamokosNuo){
        pamokosNuoBox.sendKeys(pamokosNuo);
    }

    public void sendTextPamokosIkiBox(String pamokosIki){
        pamokosIkiBox.sendKeys(pamokosIki);
    }

    public void clickSukurtiPamainaButton(){
        sukurtiPamainaButton.click();
    }

    public Boolean isSukurtiButtonDisabled(){
        return sukurtiPamainaButton.isEnabled();
    }

    public void clickAtgalToShiftsListButton(){
        atgalToShiftsListButton.click();
    }

}
