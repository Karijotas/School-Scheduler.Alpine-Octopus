package lt.itacademy.pom.SubjectPagesTests;

import lt.itacademy.SubjectPages.ArchiveSubjectPage;
import lt.itacademy.SubjectPages.SubjectsPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


public class ArchiveSubjectPageTest extends BaseTest {

    MainPage mainPage;
    ArchiveSubjectPage archiveSubjectPage;
    SubjectsPage subjectsPage;

    /**
     * Test Scneario: Check subject archiving functionality
     */


    /**
     * User can archive a subject
     * - open Dalykai page
     * - select subject and click Archive button
     * - click Taip for warning message
     * - check if subject appears in the beginning of the archived subjects list
     *
     */
    @Test
    @Tag("regression")
    public void TestUserArchivesSubjectTest(){
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        archiveSubjectPage = new ArchiveSubjectPage(driver);


        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();

        WaitUtils.waitForList(driver);
        String subjectToArchive = subjectsPage.getFirstSubjectText();
        subjectsPage.clickArchiveSubjectButton();

        subjectsPage.clickTaipButtonToArchiveSubject();

        mainPage.clickArchiveButton();
        archiveSubjectPage.clickDalykuArchyvas();
        WaitUtils.waitForList(driver);
        String firstSubjectNameInArchiveList = archiveSubjectPage.getFirstSubjectInArchiveListText();
        Assertions.assertEquals(firstSubjectNameInArchiveList, subjectToArchive, "Subject names do not match");
    }


    /**
     * User clicks Atgal button when archiving the subject
     * - open Dalykai page
     * - select a subject and click archive button
     * - click "Grizti atgal" when warning message pops up
     * - check that the subject remains in the subjects list
     */
    @Test
    @Tag("regression")
    public void TestUserDoesNotArchiveSubjectTest(){
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);
        archiveSubjectPage = new ArchiveSubjectPage(driver);


        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();

        WaitUtils.waitForList(driver);
        String subjectToArchive = subjectsPage.getFirstSubjectText();
        subjectsPage.clickArchiveSubjectButton();
        subjectsPage.clickGriztiAtgalWithoutArchivingSubject();

        String firstSubjectName = subjectsPage.getFirstSubjectText();
        Assertions.assertEquals(firstSubjectName, subjectToArchive, "Subject names do not match");
    }


    /**
     * User can restore archived subject
     * - open Dalyku archyvas
     * - select subject and click 'Atstatyti' button
     * - check if subject appears in the beginning of the subject list
     */
    @Test
    @Tag("regression")
    public void TestSubjectIsRestoredFromArchiveTest() {
        mainPage = new MainPage(driver);
        archiveSubjectPage = new ArchiveSubjectPage(driver);
        subjectsPage = new SubjectsPage(driver);

        mainPage.clickTvarkytiDuomenisItem();

        mainPage.clickArchiveButton();
        archiveSubjectPage.clickDalykuArchyvas();

        WaitUtils.waitForList(driver);
        String thirdSubjectName = archiveSubjectPage.getThirdSubjectInArchiveListText();
        archiveSubjectPage.clickToRestoreThirdSubject();
        mainPage.clickDalykaiTabItem();

        WaitUtils.waitForList(driver);
        String restoredSubjectName = subjectsPage.getFirstSubjectText();
        Assertions.assertEquals(restoredSubjectName, thirdSubjectName, "Names are not equal");
    }


}
