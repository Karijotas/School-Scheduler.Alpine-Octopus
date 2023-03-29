package lt.itacademy.TeacherPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TeacherPage extends AbstactPage {

    @FindBy(css = "tbody > tr:first-child > td:first-child")
    private WebElement firstTeacherInList;

    public TeacherPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstTeacherNameInList(){
        return firstTeacherInList.getText();
    }
}
