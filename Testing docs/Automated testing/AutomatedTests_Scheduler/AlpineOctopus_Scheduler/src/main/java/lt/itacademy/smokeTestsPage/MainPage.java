package lt.itacademy.smokeTestsPage;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;

public class MainPage extends AbstactPage {

    @FindBy(css = "div[id='menu'] > a:nth-child(2)")
    private WebElement tvarkytiDuomenisNavbarItem;

    @FindBy(css = "#menu > a[href=\"#/view/groupsSchedules\"]")
    private WebElement tvarkarasciaiNavbarItem;

    @FindBy(css = "thead > tr > th:first-child")
    private WebElement moduliaiDisplayedText;

    @FindBy(css = "thead > tr > th:first-child")
    private WebElement tvarkarasciaiTitleDisplayedText;

    @FindBy(css = "a[href=\"#/view/modules\"]")
    private WebElement moduliaiTabItem;
    @FindBy(css = "a[href=\"#/view/subjects\"]")
    private WebElement dalykaiTabItem;

    @FindBy(css = "thead > tr > th:first-child")
    private WebElement pageTitle;

    @FindBy(css = "a[href=\"#/view/teachers\"]")
    private WebElement mokytojaiTabItem;

    @FindBy(css = "a[href=\"#/view/groups\"]")
    private WebElement grupesTabItem;

    @FindBy(css = "a[href=\"#/view/shifts\"]")
    private WebElement pamainosTabItem;

    @FindBy(css = "a[href=\"#/view/rooms\"]")
    private WebElement kabinetaiTabItem;

    @FindBy(css = "a[href=\"#/view/programs\"]")
    private WebElement programosTabItem;

    @FindBy(css = "a[href=\"#/view/holidays\"]")
    private WebElement atostogosTabItem;

    @FindBy(css = "a#details")
    private WebElement kurtiNaujaKlasifikatoriu;

    @FindBy(css = "div[content=\"Archyvas\"]")
    private WebElement archiveButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }






    public void clickTvarkytiDuomenisItem(){
        tvarkytiDuomenisNavbarItem.click();
    }

    public void clickTvarkarasciaiItem(){
        tvarkarasciaiNavbarItem.click();
    }

    public String getModuliaiDisplayedText(){
        return moduliaiDisplayedText.getText();
    }

    public String getTvarkarasciaiTitleDisplayedText(){
        return tvarkarasciaiTitleDisplayedText.getText();
    }

    public void clickModuliaiTabItem(){ moduliaiTabItem.click();}
    public void clickDalykaiTabItem(){
        dalykaiTabItem.click();
    }

    public String getPageTitle(){
        return pageTitle.getText();
    }

    public void clickMokytojaiTabItem(){
        mokytojaiTabItem.click();
    }

    public void clickGrupesTabItem(){
        grupesTabItem.click();
    }

    public void clickPamainosTabItem(){
        pamainosTabItem.click();
    }

    public void clickKabinetaiTabItem(){
        kabinetaiTabItem.click();
    }

    public void clickProgramosTabItem(){
        programosTabItem.click();
    }


    public void clickKurtiNaujaKlasifikatoriu(){
        kurtiNaujaKlasifikatoriu.click();
    }

    public void clickArchiveButton(){
        archiveButton.click();
    }

    public void clickAtostogosTabItem(){
        atostogosTabItem.click();
    }



}
