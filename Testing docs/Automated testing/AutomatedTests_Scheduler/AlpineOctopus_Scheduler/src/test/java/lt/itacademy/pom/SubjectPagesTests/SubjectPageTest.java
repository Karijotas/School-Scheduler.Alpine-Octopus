package lt.itacademy.pom.SubjectPagesTests;

import lt.itacademy.SubjectPages.SubjectsPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SubjectPageTest extends BaseTest {

    private String searchSubjectByName = "daile";
    MainPage mainPage;
    SubjectsPage subjectsPage;

    /**
     * Test Scenario: Check filtering functionality for subject list
     */

    /**
     * User can filter subjects list by name
     * - open Dalykai page
     * - enter text to "Filtruoti pagal pavadinima' box
     * - check that only these subjects that match filtering text are shown
     *
     * @param subjectFromFile
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/subjectsToFilter.txt")
    public void TestUserCanFilterSubjectsByName(String subjectFromFile){
        mainPage = new MainPage(driver);
        subjectsPage = new SubjectsPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickDalykaiTabItem();
        subjectsPage.sendTextToFilterSubjectByName(subjectFromFile);

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("tbody > tr > :first-child"), subjectFromFile));

        String filteredSubject = subjectsPage.getSubjectName();
        Assertions.assertEquals(subjectFromFile, filteredSubject, "Subject name does not match");
    }


}
