package lt.itacademy.GroupPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GroupsPage extends AbstactPage {

    @FindBy(css = "table > tbody > tr:first-child > td:first-child")
    private WebElement firstGroupInTheList;

    @FindBy(css = "input[title=\"Filtruoti pagal pavadinimą\"]")
    private WebElement filterGroupByName;

    @FindBy(css = "input[title=\"Filtruoti pagal programą\"]")
    private WebElement filterGroupByProgram;

    @FindBy(css = "tbody > tr:first-child > td:nth-child(4)")
    private WebElement programNameIgGroupListPage;


//////////////////////////////////////////////////////////
    public GroupsPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstGroupInTheListName(){
        return firstGroupInTheList.getText();
    }

    public void sendTextToFilterGroupByName(String groupNameFromFile){
        filterGroupByName.click();
        filterGroupByName.sendKeys(groupNameFromFile);
    }

    public void sendTextToFilterGroupByProgram(String programName){
        filterGroupByProgram.sendKeys(programName);
    }

    public String getProgramName(){
        return programNameIgGroupListPage.getText();
    }
}
