package lt.itacademy.GroupPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateGroupPage extends AbstactPage {

    @FindBy(css = "form > div > input")
    private WebElement newGroupNameInCreateWindow;

    @FindBy(css = "#selectYear:nth-child(2) > option:nth-child(2)")
    private WebElement selectYearDropDown2option;

    @FindBy(css = "div > div > input")
    private WebElement studentuSkaiciusBox;

    @FindBy(css = "div:nth-child(3) > div:nth-child(1) > div")
    private WebElement programaDropDown;
    @FindBy(css = "div.visible.menu.transition > div:nth-child(4) > span")
    private WebElement programaDropdownSelectProgram;

    @FindBy(css = "div:nth-child(3) > div:nth-child(2) > div")
    private WebElement pamainaDropdown;

    @FindBy(css = "div.visible.menu.transition > div:nth-child(3) > span")
    private WebElement pamainaDropDownSelectShift;

    @FindBy(xpath = "//button[text()=\"Sukurti\"]")
    private WebElement sukurtiGrupeButton;

    @FindBy(css = "form > div.field > label")
    private WebElement pageTitleInCreateGroupPage;


//////////////////////////////////////////////////////////
    public CreateGroupPage(WebDriver driver) {
        super(driver);
    }


    public void clickNewGroupNameInCreateWindow(){
        newGroupNameInCreateWindow.click();
    }

    public void sendNewGroupName(String newGroupName){
        newGroupNameInCreateWindow.sendKeys(newGroupName);
    }

    public String getGroupNameInCreateWindow(){
        return newGroupNameInCreateWindow.getText();
    }

    public void clickSelectYearDropDown(){
        selectYearDropDown2option.click();
    }

    public void sendTextToStudentuSkaiciusBox(String studentCount){
        studentuSkaiciusBox.sendKeys(studentCount);
    }

    public void clickProgramaDropdown(){
        programaDropDown.click();
    }

    public void clickProgram(){
        programaDropdownSelectProgram.click();
    }

    public void clickPamainaDropdown(){
        pamainaDropdown.click();
    }

    public void clickShift(){
        pamainaDropDownSelectShift.click();
    }

    public void clickSukurtiPamainaButton(){
        sukurtiGrupeButton.click();
    }

    public Boolean isSukurtiButtonEnabled(){
        return sukurtiGrupeButton.isEnabled();
    }

    public String getPageTitleInCreateGroupWindow(){
        return pageTitleInCreateGroupPage.getText();
    }

}
