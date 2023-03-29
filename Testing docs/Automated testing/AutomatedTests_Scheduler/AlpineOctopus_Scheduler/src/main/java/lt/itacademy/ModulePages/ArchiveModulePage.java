package lt.itacademy.ModulePages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ArchiveModulePage extends AbstactPage {

    @FindBy(xpath = "//a[text()='Modulių archyvas']")
    private WebElement moduliuArchyvasTab;

    @FindBy(css = "table tbody > tr:first-child > td:first-child")
    private WebElement latestArchivedModule;

    @FindBy(css = "table tbody > tr:first-child > td:nth-child(2)")
    private WebElement restoreButtonForFirstModuleInArchiveList;

    public ArchiveModulePage(WebDriver driver) {
        super(driver);
    }


    ////////////////////////////////////////////////


    public void clickModuliuArchyvasTab(){
        moduliuArchyvasTab.click();
    }

    public String getLatestArchivedModuleName(){
        return latestArchivedModule.getText();
    }

    public void clickToRestoreFirstArchivedModuleInList(){
        restoreButtonForFirstModuleInArchiveList.click();
    }


}
