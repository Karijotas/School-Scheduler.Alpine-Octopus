package lt.itacademy.HolidaysPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HolidaysPage extends AbstactPage {

    @FindBy(css = "input[placeholder=\"Filtruoti pagal pavadinimą\"]")
    private WebElement filterHolidaysByName;

    @FindBy(css = "table > tbody > tr:first-child > td:first-child")
    private WebElement firstHolidayInList;

    public HolidaysPage(WebDriver driver) {
        super(driver);
    }

    public void sendTextToFilterHolidayByName(String holidayToFilter){
        filterHolidaysByName.sendKeys(holidayToFilter);
    }

    public String getNameForFirstHolidayInList(){
        return firstHolidayInList.getText();
    }
}
