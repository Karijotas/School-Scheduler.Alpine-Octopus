package lt.itacademy.TeacherPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditTeachersPage extends AbstactPage {

    @FindBy(xpath = "//div[2]/div/div/a")
    public WebElement atgalToTeacherListButton;

    @FindBy(css = "tbody > tr:first-child > td:first-child")
    private WebElement firstTeacherInTheList;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/table[1]/tbody/tr/td[4]/button")
    private WebElement redaguotiMokytojaButton;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/table[1]/tbody/tr/td[1]/div/input")
    private WebElement editMokytojoVardasBox;

    @FindBy(xpath = "//button[text()='Atnaujinti']")
    private WebElement atnaujintiButton;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/a")
    private WebElement atgalToTeachersListButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/table[1]/tbody/tr/td[3]")
    private WebElement lastUpdateDate;



    public EditTeachersPage(WebDriver driver) {
        super(driver);
    }

    public void clickAtgalToTeacherListButton(){
        atgalToTeacherListButton.click();
    }

    public String firstTeacherInTheList() {
        return firstTeacherInTheList.getText();
    }

    public void clickRedaguotiMokytojaButton() {
        redaguotiMokytojaButton.click();
    }

    public void sendEditedTeacherName(String editedTeacherName) {
        editMokytojoVardasBox.clear();
        editMokytojoVardasBox.sendKeys(editedTeacherName);
    }

    public void clickAtnaujintiButton() {
        atnaujintiButton.click();
    }


    public String getLastUpdateTeachersDate() {
        return lastUpdateDate.getText().substring(0,10);
    }

    public void clickAtgalToTeachersListButton() {
        atgalToTeachersListButton.click();
    }
}
