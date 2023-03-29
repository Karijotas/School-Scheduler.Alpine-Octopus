package lt.itacademy.ShiftPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShiftPage extends AbstactPage {

    @FindBy(css = "tbody > tr > td:first-child")
    private WebElement firstShiftFromTheList;

    @FindBy(css = "input[placeholder]")
    private WebElement filterShiftByName;

    @FindBy(css = "table > tbody > tr > td:first-child")
    private List<WebElement> listOfShifts;

    public ShiftPage(WebDriver driver) {
        super(driver);
    }


    public String getNameFirstShiftInTheList(){
        return firstShiftFromTheList.getText();
    }

    public void sendTextToShiftFilter(String shiftNameToFiler){
        filterShiftByName.sendKeys(shiftNameToFiler);
    }

    public List<WebElement> filteredShifts(){
        return listOfShifts;
    }



}
