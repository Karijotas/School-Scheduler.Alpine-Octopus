package lt.itacademy.SubjectPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.ThreadLocalRandom;

public class CreateSubjectPage extends AbstactPage {

    @FindBy(css = "input[placeholder='Dalyko pavadinimas']")
    private WebElement dalykoPavadinimasBox;

    @FindBy(css = "input[placeholder='Aprašymas']")
    private WebElement dalykoAprasymasBox;

    @FindBy(css = "button[id='details']")
    private WebElement sukurtiDalykaButton;

    @FindBy(css = "div > div[style]")
    private WebElement errorMsgInCreateSubject;


    public CreateSubjectPage(WebDriver driver) {
        super(driver);
    }

    public void clickDalykoPavadinimasBox(){
        dalykoPavadinimasBox.click();

    }
    public void sendTextToDalykoPavadinimas(String subjectName){
        dalykoPavadinimasBox.click();
        dalykoPavadinimasBox.sendKeys(subjectName);
    }

    public void clickDalykoAprasymasBox(){
        dalykoAprasymasBox.click();
    }
    public void sendTextToDalykoAprasymas(String subjectDescription){
        dalykoAprasymasBox.click();
        dalykoAprasymasBox.sendKeys(subjectDescription);
    }

    public void clickSukurtiDalykaButton(){
        sukurtiDalykaButton.click();
    }

    public String getErrorMsgTextInCreateSubject(){
        return errorMsgInCreateSubject.getText();
    }



}
