package lt.itacademy.SchedulesPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditSchedulePage extends AbstactPage {

    @FindBy(css = "table:first-child > tbody > tr > td.six.wide")
    private WebElement scheduleNameInViewSchedulePage;

    @FindBy(css = "#printBtn")
    public WebElement printButtonInViewScheduleWindow;

    public EditSchedulePage(WebDriver driver) {
        super(driver);
    }

    public String getScheduleNameInViewSchedulePage(){
        return scheduleNameInViewSchedulePage.getText();
    }

    public Boolean isPrintButtonInViewScheduleWindowClickable(){
        return printButtonInViewScheduleWindow.isEnabled();
    }
}
