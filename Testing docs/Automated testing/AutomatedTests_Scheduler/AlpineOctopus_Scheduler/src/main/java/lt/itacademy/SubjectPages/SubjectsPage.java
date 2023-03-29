package lt.itacademy.SubjectPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SubjectsPage extends AbstactPage {

    @FindBy(xpath = "//*[@id=\"subjects\"]/table/tbody/tr[1]/td[1]")
    private WebElement firstSubjectInTheList;

    @FindBy(xpath = "//*[@id=\"subjects\"]/table/tbody/tr[3]/td[1]")
    private WebElement thirdSubjectInTheList;


    @FindBy(css = "input[placeholder='Filtruoti pagal dalyką']")
    private WebElement filterSubjectByName;

    @FindBy(css = "tbody > tr > :first-child")
    private List<WebElement> subjectsInTheList;

    @FindBy(css = "tbody > tr > :first-child")
    private WebElement subjectInTheList;

    @FindBy(css = "tbody > tr:nth-child(1) > td:nth-child(2) > a")
    private WebElement viewFirstSubjectButton;
    @FindBy(css = "tbody > tr:nth-child(3) > td:nth-child(2) > a")
    private WebElement viewThirdSubjectButton;

    @FindBy(css = "tbody > tr:first-child > td:nth-child(2) > button")
    private WebElement archiveFirstSubjectButton;

    @FindBy(css = "div:nth-child(12) > div > div.actions > button.ui.primary.button")
    private WebElement buttonTaipToArchiveSubject;

    @FindBy(css = "div:nth-child(12) > div > div.actions > button.ui.button:first-child")
    private WebElement buttonGriztiAtgalWithoutArchivingSubject;

////////////////////////////////////////////////////////
    public SubjectsPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstSubjectText(){
        return firstSubjectInTheList.getText();
    }

    public String getThirdSubjectText(){
        return thirdSubjectInTheList.getText();
    }

    public void clickViewThirdSubject(){
        viewThirdSubjectButton.click();
    }

    public void clickViewFirstSubject(){
        viewFirstSubjectButton.click();
    }

    public void sendTextToFilterSubjectByName(String searchSubjectByName){
        filterSubjectByName.click();
        filterSubjectByName.sendKeys(searchSubjectByName);
    }

    public String getSubjectName(){
        return subjectInTheList.getText();
    }

    public void clickArchiveSubjectButton(){
        archiveFirstSubjectButton.click();
    }



    public void clickTaipButtonToArchiveSubject(){
        buttonTaipToArchiveSubject.click();
    }

    public void clickGriztiAtgalWithoutArchivingSubject(){
        buttonGriztiAtgalWithoutArchivingSubject.click();
    }



}
