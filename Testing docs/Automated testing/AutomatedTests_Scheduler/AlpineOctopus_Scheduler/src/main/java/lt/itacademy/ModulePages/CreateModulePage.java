package lt.itacademy.ModulePages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateModulePage extends AbstactPage {

    @FindBy(css = "form:first-child > div > input")
    private WebElement moduleNameInCreateWindow;

    @FindBy(css = "form > div:nth-child(2) > div > input")
    private WebElement moduleDescriptionInCreateWindow;

    @FindBy(xpath = "//button[text()=\"Sukurti\"]")
    private WebElement sukurtiNewModule;

    @FindBy(xpath = "//a[text()=\"Atgal\"]")
    private WebElement atgalToModulesListButton;

    @FindBy(css = "form > div:nth-child(1) > div")
    private WebElement warningMsgForBlankModuleName;


    ///////////////////////////////////////////
    public CreateModulePage(WebDriver driver) {
        super(driver);
    }

    public void clickModuleNameInCreateWindow(){
        moduleNameInCreateWindow.click();
    }

    public void sendNewModuleName(String newModuleName){
        moduleNameInCreateWindow.sendKeys(newModuleName);
    }

    public void clickModuleDescriptionInCreateWindow(){
        moduleDescriptionInCreateWindow.click();
    }

    public void sendNewModuleDescription(String newModuleDescription){
        moduleDescriptionInCreateWindow.sendKeys(newModuleDescription);
    }

    public void clickSukurtiNewModule(){
        sukurtiNewModule.click();
    }

    public Boolean isSukurtiButtonDisabled(){
        return sukurtiNewModule.isEnabled();
    }

    public String getWarningMsgTextForBlankModuleName(){
        return warningMsgForBlankModuleName.getText();
    }

    public void clickAtgalToModulesListButton(){
        atgalToModulesListButton.click();
    }
}
