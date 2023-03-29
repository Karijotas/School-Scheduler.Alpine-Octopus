package lt.itacademy.ProgramPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateProgramPage extends AbstactPage {

    @FindBy(css = "input[placeholder]")
    private WebElement programosPavadinimasBox;

    @FindBy(xpath = "//button[text()=\"Sukurti\"]")
    private WebElement sukurtiProgramButton;

    @FindBy(css = "form > div:nth-child(1) > div")
    private WebElement warningForEmptyProgramName;

    @FindBy(css = "textarea[placeholder]")
    private WebElement programDescriptionField;

    public CreateProgramPage(WebDriver driver) {
        super(driver);
    }

    public void clickProgramNameField(){
        programosPavadinimasBox.click();
    }
    public void sendTextToProgramosPavadinimas(String newProgramName){
        programosPavadinimasBox.sendKeys(newProgramName);
    }

    public void clickSukurtiProgramButton(){
        sukurtiProgramButton.click();
    }

    public Boolean isSukurtiButtonEnabled(){
        return sukurtiProgramButton.isEnabled();
    }

    public String getWarningTextForEmptyProgramName(){
        return warningForEmptyProgramName.getText();
    }

    public void clickProgramDescriptionField(){
        programDescriptionField.click();
    }
}
