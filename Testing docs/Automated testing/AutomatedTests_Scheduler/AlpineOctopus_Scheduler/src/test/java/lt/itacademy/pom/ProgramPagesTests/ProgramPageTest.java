package lt.itacademy.pom.ProgramPagesTests;

import lt.itacademy.ProgramPages.EditProgramPage;
import lt.itacademy.ProgramPages.ProgramPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProgramPageTest extends BaseTest {
    MainPage mainPage;
    ProgramPage programPage;
    EditProgramPage editProgramPage;

    /**
     * User can view program information
     * - open Programos page
     * - get first program in list name
     * - click to view first program information
     * - check if program name in view window matches first program in list name
     */

    @Test
    @Tag("regression")
    public void TestUserCanViewProgramInformation(){
        mainPage = new MainPage(driver);
        programPage = new ProgramPage(driver);
        editProgramPage = new EditProgramPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickProgramosTabItem();
        WaitUtils.waitForList(driver);
        String firstProgramInListName = programPage.getFirstProgramName();
        programPage.clickFirstProgramViewButton();
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("table.ui.celled.table > tbody > tr > td:nth-child(1)"), firstProgramInListName));
        String programNameInViewWindow = editProgramPage.getProgramNameInViewWindow();

        Assertions.assertEquals(firstProgramInListName, programNameInViewWindow, "Program names do not match");
    }


}
