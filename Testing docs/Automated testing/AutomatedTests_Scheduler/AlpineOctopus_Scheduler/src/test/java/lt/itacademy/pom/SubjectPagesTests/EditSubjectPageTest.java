package lt.itacademy.pom.SubjectPagesTests;

import lt.itacademy.SubjectPages.EditSubjectPage;
import lt.itacademy.SubjectPages.SubjectsPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class EditSubjectPageTest extends BaseTest {

    private String editedSubjectNameToSend = "Lietuvių kalba";
    private String editedSubjectDescription = "Lietuvių kalba ir literatūra";
    MainPage mainPage;
    SubjectsPage subjectsPage;
    EditSubjectPage editSubjectPage;

    /**
     * Test Scenario: Check 'view subject' and 'edit subject' functionality
     */


    /**
     * User can view subject information
     * - open Dalykai page
     * - select subject and click 'view subject' button
     * - check that the opened subject name matches the subject from the list
     */
    @Test
    @Tag("regression")
    public void TestViewSubjectTest(){

        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        editSubjectPage = new EditSubjectPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();

        WaitUtils.waitForList(driver);
        String subjectName = subjectsPage.getThirdSubjectText();
        subjectsPage.clickViewThirdSubject();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("table:nth-child(1) > tbody > tr > td:nth-child(1)"), subjectName));
        String viewedSubjectName = editSubjectPage.getViewedSubjectName();
        Assertions.assertEquals(viewedSubjectName, subjectName, "Subject names do not match");
    }


    /**
     * User can edit subject information
     * - open Dalykai page
     * - select subject and click 'view subject' button
     * - click Redaguoti button
     * - update subject name
     * - click 'Atnaujinti' button
     * - check the updated name is shown
     * - check the last update date matches current date when the update is made
     *
     */
    @Test
    @Tag("regression")
    public void TestEditSubjectTest() throws InterruptedException, ParseException {
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        editSubjectPage = new EditSubjectPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();

        WaitUtils.waitForList(driver);
        subjectsPage.clickViewThirdSubject();
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElement(editSubjectPage.lastUpdateSubjectDate, "-"));

        String originalLastUpdateSubjectDate = editSubjectPage.getLastUpdateSubjectDate();
        editSubjectPage.clickRedaguotiDalykaButton();
        Thread.sleep(2000);
        editSubjectPage.clearSubjectNameBox();
        editSubjectPage.sendEditedSubjectName(editedSubjectNameToSend);

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody > tr > td > form > textarea")));

        editSubjectPage.sendEditedSubjectDescription(editedSubjectDescription);

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Atnaujinti']")));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        editSubjectPage.clickAtnaujintiButton();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(editSubjectPage.lastUpdateSubjectDate, originalLastUpdateSubjectDate)));
        String lastUpdateSubjectDate = editSubjectPage.getLastUpdateSubjectDate();
        Date displayedDate = sdf.parse(lastUpdateSubjectDate);
        WaitUtils.waitForList(driver);
        Assertions.assertTrue(sdf.parse(originalLastUpdateSubjectDate).before(displayedDate));

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/div/div/a")));
        editSubjectPage.clickAtgalToSubjectListButton();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"subjects\"]/table/tbody/tr[1]/td[1]")));
        String editedSubjectName = subjectsPage.getFirstSubjectText();

        Assertions.assertEquals(editedSubjectName, editedSubjectNameToSend, "Names do not match");
    }

}
