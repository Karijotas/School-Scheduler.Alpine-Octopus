package lt.itacademy.ModulePages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditModulePage extends AbstactPage {

    @FindBy(css = "#segment > div > table:nth-child(1) > tbody > tr > td:nth-child(1)")
    private WebElement moduleNameInViewWindow;

    @FindBy(css = "button[id=\"details\"]")
    private WebElement redaguotiModuleButton;

    @FindBy(css = "div.ui.input > input")
    private WebElement moduleNameInEditWindow;

    @FindBy(xpath = "//button[text()=\"Atšaukti\"]")
    private WebElement atsauktiModuleEditButton;

    @FindBy(css = "tbody > tr > td:nth-child(2)")
    private WebElement lastModuleUpdateDateAndTime;

    @FindBy(xpath = "//button[text()=\"Atnaujinti\"]")
    private WebElement atnaujintiModuleEditButton;

    @FindBy(xpath = "//a[text()=\"Atgal\"]")
    private WebElement atgalToModulesListButton;



    public EditModulePage(WebDriver driver) {
        super(driver);
    }

    public String getModuleNameInViewWindow(){
        return moduleNameInViewWindow.getText();
    }

    public void clickRedaguotiModuleButton(){
        redaguotiModuleButton.click();
    }

    public void clearModuleNameInEditWindow(){
        moduleNameInEditWindow.clear();
    }
    public void sendUpdatedModuleName(String updatedModuleName){
    //    moduleNameInEditWindow.clear();
       moduleNameInEditWindow.sendKeys(updatedModuleName);
    }

    public void clickAtsauktiModuleEditButton(){
        atsauktiModuleEditButton.click();
    }

    public String getLastModuleUpdateDateAndTimeText(){
        return lastModuleUpdateDateAndTime.getText();
    }

    public void clickAtnaujintiModuleButton(){
        atnaujintiModuleEditButton.click();
    }

    public void clickAtgalToModulesListButton(){
        atgalToModulesListButton.click();
    }



}
