package lt.itacademy.TeacherPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArchiveTeacherPage extends AbstactPage {

    @FindBy(css = "div[content=\"Archyvas\"]")
    private WebElement archiveButton;

    @FindBy(xpath = "//div[2]/a[3]")
    private WebElement mokytojuArchyvasTab;

    @FindBy(css = "tbody > tr:first-child > td:first-child")
    private WebElement firstTeacherInTheArchiveList;

    @FindBy(css = "tbody > tr:nth-child(1) > td:nth-child(2)")
    private WebElement firstTeacherRestoreButton;


    public ArchiveTeacherPage(WebDriver driver) {
        super(driver);
    }

    public void clickArchiveTeacherButton() {
        archiveButton.click();
    }

    public void clickMokytojuArchyvas() {
        mokytojuArchyvasTab.click();
    }

    public String getFirstTeacherInArchiveListText() {
        return firstTeacherInTheArchiveList.getText();
    }

    public void clickToRestoreFirstTeacher() {
        firstTeacherRestoreButton.click();
    }

}
