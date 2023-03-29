package lt.itacademy.TeacherPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateTeacherPage extends AbstactPage {

    @FindBy(css = "form > div > input[placeholder=\"Vardas ir pavardė\"]")
    private WebElement vardasPavardeForNewTeacher;

    @FindBy(css = "form > div > input[placeholder=\"El. paštas\"]")
    private WebElement teamsNameForNewTeacher;

    @FindBy(css = "form > div > input[placeholder=\"Teams vartotojo vardas\"]")
    private WebElement elPastasForNewTeacher;

    @FindBy(css = "form > div > input[placeholder=\"Telefono nr.\"]")
    private WebElement telefonoNrForNewTeacher;

    @FindBy(css = "button[id='details']")
    private WebElement sukurtiMokytojaButton;

    @FindBy(css = "form > div:first-child > div")
    private WebElement warningMsgForEmptyTeacherName;

    public CreateTeacherPage(WebDriver driver) {
        super(driver);
    }

    public void sendTextToVardasPavardeForNewTeacher(String newTeacherName){
        vardasPavardeForNewTeacher.sendKeys(newTeacherName);
    }

    public void sendTextToTeamsNameForNewTeacher(String newTeacherTeamsName){
        teamsNameForNewTeacher.sendKeys(newTeacherTeamsName);
    }

    public void sendTextToElPastasForNewTeacher(String newTeacherEmail){
        elPastasForNewTeacher.sendKeys(newTeacherEmail);
    }

    public void sendTextToTelefonoNrForNewTeacher(String newTeacherPhoneNo){
        telefonoNrForNewTeacher.sendKeys(newTeacherPhoneNo);
    }

    public void clickSukurtiMokytojaButton(){
        sukurtiMokytojaButton.click();
    }

    public void clickVardasPavardeForNewTeacherBox(){
        vardasPavardeForNewTeacher.click();
    }

    public void clickTeamsNameForNewTeacher(){
        teamsNameForNewTeacher.click();
    }

    public String getWarningMsgForEmptyTeacherName(){
        return warningMsgForEmptyTeacherName.getText();
    }

    public Boolean isSukurtiButtonEnabled(){
        return sukurtiMokytojaButton.isEnabled();
    }

}
