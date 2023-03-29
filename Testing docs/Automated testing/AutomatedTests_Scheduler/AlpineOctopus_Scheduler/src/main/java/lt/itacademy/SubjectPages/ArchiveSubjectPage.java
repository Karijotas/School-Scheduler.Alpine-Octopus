package lt.itacademy.SubjectPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArchiveSubjectPage extends AbstactPage {

    @FindBy(xpath = "//div[2]/a[2]")
    private WebElement dalykuArchyvasTab;

    @FindBy(css = "tbody > tr:first-child > td:first-child")
    private WebElement firstSubjectInTheArchiveList;

    @FindBy(css = "tbody > tr:nth-child(3) > td")
    private WebElement thirdSubjectInArchiveList;

    @FindBy(css = "tbody > tr:nth-child(3) > td:nth-child(2)")
    private WebElement thirdSubjectRestoreButton;





    ///////////////////////////////////////////////////////////
    public ArchiveSubjectPage(WebDriver driver) {
        super(driver);
    }




    public void clickDalykuArchyvas(){
        dalykuArchyvasTab.click();
    }

    public String getFirstSubjectInArchiveListText(){
        return firstSubjectInTheArchiveList.getText();
    }

    public String getThirdSubjectInArchiveListText(){
        return thirdSubjectInArchiveList.getText();
    }

    public void clickToRestoreThirdSubject(){
        thirdSubjectRestoreButton.click();
    }


}
