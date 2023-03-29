package lt.itacademy.HolidaysPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateHolidaysPage extends AbstactPage {

    @FindBy(css = "input[placeholder=\"Atostogų pavadinimas\"]")
    private WebElement newHolidayNameBox;

    @FindBy(css = "div > input[value=\"yes\"]")
    private WebElement taipButtonForRepeatedHoliday;

    @FindBy(css = "form > div > div")
    private WebElement warningMsgForEmptyHolidayName;

    @FindBy(css = "button#details")
    private WebElement sukurtiHolidaysButton;

    public CreateHolidaysPage(WebDriver driver) {
        super(driver);
    }

    public void clickNewHolidayNameBox(){
        newHolidayNameBox.click();
    }

    public void clickTaipButtonForRepeatedHoliday(){
        taipButtonForRepeatedHoliday.click();
    }

    public String  getTextForWarningMsgForEmptyHolidayName(){
        return warningMsgForEmptyHolidayName.getText();
    }

    public Boolean isSukurtiHolidaysButtonEnabled(){
        return sukurtiHolidaysButton.isEnabled();
    }


}
