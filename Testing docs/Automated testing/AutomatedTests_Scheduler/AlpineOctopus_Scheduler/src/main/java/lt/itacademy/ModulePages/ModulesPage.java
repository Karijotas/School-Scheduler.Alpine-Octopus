package lt.itacademy.ModulePages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModulesPage extends AbstactPage {

    @FindBy(xpath = "//a[text()=\"Moduliai\"]")
    private WebElement moduliaiTab;

    @FindBy(xpath = "//*[@id=\"modules\"]/table/tbody/tr[2]/td[1]")
    private WebElement secondModuleInTheList;

    @FindBy(css = "tbody > tr:nth-child(2) > td:nth-child(2) > a")
    private WebElement viewSecondModuleInTheListButton;

    @FindBy(css = "tbody > tr:nth-child(2) > td:nth-child(2) > button")
    private WebElement archiveSecondModuleButton;

    @FindBy(css = "div .content")
    private WebElement warningMessageForArchive;

    @FindBy(css = "div:nth-child(12) > div > div.actions > button.ui.primary.button")
    private WebElement buttonTaipToArchiveModule;

    @FindBy(xpath = "//div[11]/div/div[3]/button[1]")
    private WebElement griztiAtgalFromWarningMessageButton;

    @FindBy(xpath = "//*[@id=\"modules\"]/table/tbody/tr[1]/td[1]")
    private WebElement firstModuleInTheList;

    public ModulesPage(WebDriver driver) {
        super(driver);
    }


    //////////////////////////////////////////////////

    public String getSecondModulesName(){
        return secondModuleInTheList.getText();
    }

    public void clickToViewSecondModuleInTheList(){
        viewSecondModuleInTheListButton.click();
    }

    public void clickToArchiveSecondModuleButton(){
        archiveSecondModuleButton.click();
    }

    public String getWarningMessageText(){
        return warningMessageForArchive.getText();
    }
    public void clickYesToArchiveModule(){
        buttonTaipToArchiveModule.click();
    }

    public String getFirstModulesName(){
        return firstModuleInTheList.getText();
    }

    public void openModuliaiTab(){
        moduliaiTab.click();
    }

    public void clickGriztiAtgalFromWarningMessageButton(){
        griztiAtgalFromWarningMessageButton.click();
    }

}
