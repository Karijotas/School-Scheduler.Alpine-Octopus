package lt.itacademy.ProgramPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProgramPage extends AbstactPage {

    @FindBy(css = "tbody > tr:first-child > td:first-child")
    private WebElement firstProgramInList;

    @FindBy(css = "tbody > tr:first-child > td:nth-child(2) > a")
    private WebElement viewFirstProgramButton;


    public ProgramPage(WebDriver driver) {
        super(driver);
    }


    public String getFirstProgramName(){
        return firstProgramInList.getText();
    }

    public void clickFirstProgramViewButton(){
        viewFirstProgramButton.click();
    }
}
