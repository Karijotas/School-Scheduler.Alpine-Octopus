package lt.itacademy.ProgramPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditProgramPage extends AbstactPage {

    @FindBy(xpath = "//a[text()=\"Atgal\"]")
    private WebElement atgalToProgramListButton;

    @FindBy(css = "table.ui.celled.table > tbody > tr > td:nth-child(1)")
    private WebElement programNameInViewWindow;

    public EditProgramPage(WebDriver driver) {
        super(driver);
    }

    public void clickAtgalToProgramListButton(){
        atgalToProgramListButton.click();
    }

    public String getProgramNameInViewWindow(){
        return programNameInViewWindow.getText();
    }

}
