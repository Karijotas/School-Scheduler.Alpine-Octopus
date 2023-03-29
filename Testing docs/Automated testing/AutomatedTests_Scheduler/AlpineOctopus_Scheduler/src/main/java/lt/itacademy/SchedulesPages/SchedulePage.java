package lt.itacademy.SchedulesPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SchedulePage extends AbstactPage {

    @FindBy(css = "table > tbody > tr:nth-child(3) > td:nth-child(5) > a")
    private WebElement thirdScheduleFromListViewButton;

    @FindBy(css = "table > tbody > tr:nth-child(3) > td:first-child")
    private WebElement thirdScheduleFromList;

    @FindBy(css = "table >tbody > tr > td:first-child")
    private WebElement scheduleFromList;

    @FindBy(css = "table >tbody > tr > td:first-child")
    private List<WebElement> schedulesList;

    @FindBy(css = "div .input > input")
    private WebElement filterScheduleByNameBox;
    public SchedulePage(WebDriver driver) {
        super(driver);
    }


    public void clickToSelect3rdScheduleFromList(){
        thirdScheduleFromListViewButton.click();
    }

    public String getThirdScheduleNameFromList(){
        return thirdScheduleFromList.getText();
    }

    public void sendTextToFilterScheduleByNameBox(String scheduleToFilter){
        filterScheduleByNameBox.click();
        filterScheduleByNameBox.sendKeys(scheduleToFilter);
    }

    public String getScheduleName(){
        return scheduleFromList.getText();
    }

    public List<WebElement> searchedSchedules(){
        return schedulesList;
    }
}
