package lt.itacademy.SchedulesPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateSchedulePage extends AbstactPage {

    @FindBy(css = "div > a#details")
    private WebElement kurtiNaujaSchedule;

    @FindBy(css = "a[title=\"Sukurti grupę\"]")
    private WebElement kurtiNaujaGrupeInCreateScheduleWindow;


    public CreateSchedulePage(WebDriver driver) {
        super(driver);
    }

    public void clickKurtiNaujaSchedule(){
        kurtiNaujaSchedule.click();
    }

    public void clickKurtiNaujaGrupeInCreateScheduleWindow(){
        kurtiNaujaGrupeInCreateScheduleWindow.click();
    }
}
