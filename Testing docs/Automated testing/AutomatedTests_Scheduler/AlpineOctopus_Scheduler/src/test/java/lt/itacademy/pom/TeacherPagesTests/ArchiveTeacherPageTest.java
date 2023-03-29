package lt.itacademy.pom.TeacherPagesTests;

import lt.itacademy.TeacherPages.ArchiveTeacherPage;
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

public class ArchiveTeacherPageTest extends BaseTest {

    MainPage mainPage;
    ArchiveTeacherPage archiveTeacherPage;
    TeachersPage teachersPage;

    /**
     * Test Scenario: Check archiving teacher functionality
     */

    /**
     * User can archive teacher
     * - open Mokytojai page
     * - store first teacher's name
     * - click archive button for the first teacher
     * - click Taip when warning message pops up
     * - open Mokytoju archyvas
     * - check that the archived teacher's name is in the beginning of the archived teacher's list
     */
    @Test
    public void teacherIsArchivedTest() {
        mainPage = new MainPage(driver);
        teachersPage = new TeachersPage(driver);
        archiveTeacherPage = new ArchiveTeacherPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        teachersPage.clickMokytojaiMenuTab();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"teacher\"]/table/tbody/tr[1]/td[1]")));
        String teacherToArchive = teachersPage.getFirstTeacherText();

        teachersPage.clickSuarchyvuotiButton();

        teachersPage.clickTaipButtonToArchiveTeacher();

        archiveTeacherPage.clickArchiveTeacherButton();
        archiveTeacherPage.clickMokytojuArchyvas();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody > tr:first-child > td:first-child")));
        String firstTeacherNameInArchiveList = archiveTeacherPage.getFirstTeacherInArchiveListText();

        Assertions.assertEquals( firstTeacherNameInArchiveList, teacherToArchive, "Teacher names do not match");
    }


    /**
     * User does not archive teacher
     * - open Mokytojai page
     * - store first teacher's name
     * - click archive button for the first teacher
     * - click Grizti atgal when warning message pops up
     * - check if the same teacher name is in the first row
     */
    @Test
    public void teacherIsNotArchivedTest() {
        mainPage = new MainPage(driver);
        teachersPage = new TeachersPage(driver);
        archiveTeacherPage = new ArchiveTeacherPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        teachersPage.clickMokytojaiMenuTab();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"teacher\"]/table/tbody/tr[1]/td[1]")));
        String teacherToArchive = teachersPage.getFirstTeacherText();
        teachersPage.clickArchiveTeacherButton();
//        new WebDriverWait(driver, Duration.ofSeconds(2))
//                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > div:nth-child(11) > div > div.actions > button:nth-child(1)")));
        teachersPage.clickGriztiAtgalWithoutArchivingTeacher();
        String firstTeacherName = teachersPage.getFirstTeacherText();

        Assertions.assertEquals(firstTeacherName, teacherToArchive, "Teacher names do not match");
    }

    /**
     * User can restore teacher from archive
     * - open Mokytoju archyvas
     * - store first teacher's name
     * - click Restore button
     * - open Mokytojai page
     * - check that the archived teacher's name is in the beginning of the teacher's list
     */
    @Test
    public void teacherIsRestoredFromArchiveTest() {
        mainPage = new MainPage(driver);
        teachersPage = new TeachersPage(driver);
        archiveTeacherPage = new ArchiveTeacherPage(driver);

        mainPage.clickTvarkytiDuomenisItem();

        archiveTeacherPage.clickArchiveTeacherButton();
        archiveTeacherPage.clickMokytojuArchyvas();

        WaitUtils.waitForList(driver);
        String firstTeacherName = archiveTeacherPage.getFirstTeacherInArchiveListText();
        archiveTeacherPage.clickToRestoreFirstTeacher();
        teachersPage.clickMokytojaiMenuTab();

        WaitUtils.waitForList(driver);
        String restoredTeacherName = teachersPage.getFirstTeacherText();

        Assertions.assertEquals(restoredTeacherName, firstTeacherName, "Names are not equal");
    }

 }


