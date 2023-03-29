package lt.itacademy.pom.TeacherPagesTests;


import lt.itacademy.TeacherPages.EditTeachersPage;
import lt.itacademy.TeacherPages.TeacherPage;
import lt.itacademy.TeacherPages.TeachersPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.time.LocalDate;

public class EditTeachersPageTest extends BaseTest {
    private String editedTeacherNameToSend = "Vardas Pavardenis";
    MainPage mainPage;
    TeachersPage teachersPage;
    EditTeachersPage editTeacherPage;


    /**
     * User can view teacher information
     * - open Mokytojai page
     * - store first teacher's name
     * - click View button for the first teacher
     * - check if the name in view window matches first teacher's in the list name
     */
    @Test
    public void ViewTeacherTest() {
        mainPage = new MainPage(driver);
        teachersPage = new TeachersPage(driver);
        editTeacherPage = new EditTeachersPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        teachersPage.clickMokytojaiMenuTab();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"teacher\"]/table/tbody/tr[1]/td[1]")));
        String teacherName = teachersPage.getFirstTeacherText();
        teachersPage.clickPerziuretiButton();
        WaitUtils.waitForList(driver);
        String viewedTeacherName = editTeacherPage.firstTeacherInTheList();
        Assertions.assertEquals(viewedTeacherName, teacherName, "Teacher names do not match");
    }


    /**
     * User can edit teacher's name
     * - open Mokytojai page
     * - click view button to view first teacher's information
     * - click Redaguoti button
     * - write edited teacher's name
     * - click Atnaujinti button
     * - store last update date and time
     * - check if the date match current date when the update is made
     * - click Atgal button to go to the teachers list
     * - check if the first name in the list matches the edited teacher's name
     *
     * @throws InterruptedException
     */
    @Test
    public void EditTeachersTest() throws InterruptedException {
        mainPage = new MainPage(driver);
        teachersPage = new TeachersPage(driver);
        editTeacherPage = new EditTeachersPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        teachersPage.clickMokytojaiMenuTab();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"teacher\"]/table/tbody/tr[1]/td[1]")));
        teachersPage.clickViewFirstTeacher();

        teachersPage.clickPerziuretiButton();
        editTeacherPage.clickRedaguotiMokytojaButton();
        Thread.sleep(10000);

        editTeacherPage.sendEditedTeacherName(editedTeacherNameToSend);

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/table[1]/tbody/tr/td[1]/div/input")));


        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Atnaujinti']")));

        editTeacherPage.clickAtnaujintiButton();

        String lastUpdateTeacherDate = editTeacherPage.getLastUpdateTeachersDate();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div/table[1]/tbody/tr/td[3]")));

        String currentDate = LocalDate.now().toString();

        Assertions.assertEquals(currentDate, lastUpdateTeacherDate, "Last update date does not match to current date");

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/a")));
        editTeacherPage.clickAtgalToTeachersListButton();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"teacher\"]/table/tbody/tr[1]/td[1]")));
        String editedTeacherName = teachersPage.getFirstTeacherText();

        Assertions.assertEquals(editedTeacherName, editedTeacherNameToSend, "Names do not match");
    }

}
