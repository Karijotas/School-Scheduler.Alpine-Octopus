package lt.itacademy.SubjectPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditSubjectPage extends AbstactPage {


    @FindBy(css = "table:nth-child(1) > tbody > tr > td:nth-child(1)")
    private WebElement viewedSubjectName;

    @FindBy(css = "table:nth-child(1) > tbody > tr > td:nth-child(3)")
    private WebElement redaguotiDalykaButton;

    @FindBy(css = "div .ui.input > input")
    private WebElement editDalykoPavadinimasBox;

    @FindBy(css = "tbody > tr > td > form > textarea")
    private WebElement editDalykoAprasymasBox;

    @FindBy(xpath = "//div[2]/div/div/a")
    private WebElement atgalToSubjectListButton;

    @FindBy(xpath = "//button[text()='Atnaujinti']")
    private WebElement atnaujintiButton;

    @FindBy(css = "table:nth-child(1) > tbody > tr > td:nth-child(2)")
    public WebElement lastUpdateSubjectDate;


    public EditSubjectPage(WebDriver driver) {
        super(driver);
    }

    public String getViewedSubjectName(){
        return viewedSubjectName.getText();
    }

    public void clickRedaguotiDalykaButton(){
        redaguotiDalykaButton.click();
    }

    public void clickAtgalToSubjectListButton(){
        atgalToSubjectListButton.click();
    }

    public void clearSubjectNameBox(){
        editDalykoPavadinimasBox.clear();
    }
    public void sendEditedSubjectName(String editedSubjectName){
         editDalykoPavadinimasBox.sendKeys(editedSubjectName);
    }

    public void sendEditedSubjectDescription(String editedSubjectDescription){
        editDalykoAprasymasBox.clear();
        editDalykoAprasymasBox.sendKeys(editedSubjectDescription);
    }

    public void clickAtnaujintiButton(){
        atnaujintiButton.click();
    }

    public String getLastUpdateSubjectDate(){
        return lastUpdateSubjectDate.getText();
    }
}
