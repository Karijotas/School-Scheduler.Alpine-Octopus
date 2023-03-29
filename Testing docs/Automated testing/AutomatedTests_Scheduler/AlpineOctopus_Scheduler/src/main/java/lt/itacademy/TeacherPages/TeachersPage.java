package lt.itacademy.TeacherPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TeachersPage extends AbstactPage {


    @FindBy(xpath = "//*[@id=\"icocolor\"][3]")
    private WebElement mokytojaiMenuTab;

    @FindBy(css = "a[id='details']")
    private WebElement kurtiNaujaMokytojaButton;

    @FindBy(xpath = "//*[@id=\"teacher\"]/table/tbody/tr[1]/td[1]")
    private WebElement firstTeacherInTheList;

    @FindBy(css = "input[placeholder='Filtruoti pagal dalyką']")
    private WebElement filterTeacherByName;

    @FindBy(css = "tbody > tr:first-child > td:nth-child(5) > button")
    private WebElement archiveFirstTeacherButton;

    @FindBy(css = "div:nth-child(12) > div > div.actions > button.ui.primary.button")
    private WebElement buttonTaipToArchiveTeacher;

    @FindBy(css = "body > div:nth-child(12) > div > div.actions > button:nth-child(1)")
    private WebElement griztiAtgalWithoutArchivingTeacherButton;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[5]/button")
    private WebElement buttonSuarchyvuoti;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/table/tbody/tr[1]/td[5]/a")
    private WebElement buttonPerziureti;


    public TeachersPage(WebDriver driver) {
        super(driver);
    }

    public void clickMokytojaiMenuTab() {
        mokytojaiMenuTab.click();}

    public void clickKurtiNaujaMokytojaButton() {
        kurtiNaujaMokytojaButton.click();}

    public String getFirstTeacherText() {
        return firstTeacherInTheList.getText();}


    public void clickTaipButtonToArchiveTeacher() {
        buttonTaipToArchiveTeacher.click();}

    public void clickSuarchyvuotiButton() {
        buttonSuarchyvuoti.click();}

    public void clickArchiveTeacherButton() {

        archiveFirstTeacherButton.click();
    }

    public void clickGriztiAtgalWithoutArchivingTeacher() {
        griztiAtgalWithoutArchivingTeacherButton.click();
    }

    public String clickViewFirstTeacher() {
        return firstTeacherInTheList.getText();
    }

    public void clickRedaguotiMokytojaButton() {
    }

    public void clickPerziuretiButton() {
        buttonPerziureti.click();

    }
}







