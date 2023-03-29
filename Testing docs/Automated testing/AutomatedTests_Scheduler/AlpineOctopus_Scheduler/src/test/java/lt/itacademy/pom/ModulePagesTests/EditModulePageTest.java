package lt.itacademy.pom.ModulePagesTests;

import lt.itacademy.ModulePages.EditModulePage;
import lt.itacademy.ModulePages.ModulesPage;
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
import java.time.LocalDate;

public class EditModulePageTest extends BaseTest {

    MainPage mainPage;
    ModulesPage modulesPage;
    EditModulePage editModulePage;

    /**
     * Test Scenario: Check module edit functionality
     */

    /**
     * User cancels module update
     * - open Moduliai page
     * - click to view a second module
     * - click Redaguoti
     * - update module name
     * - click Atsaukti
     * - check if last update date has not changed
     *
     * @throws InterruptedException
     */
    @Test
    @Tag("regression")
    public void TestModuleInformationUpdateIsCancelled() throws InterruptedException {

        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        editModulePage = new EditModulePage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        WaitUtils.waitForList(driver);
        String secondModulesName = modulesPage.getSecondModulesName();
        modulesPage.clickToViewSecondModuleInTheList();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#segment > div > table:nth-child(1) > tbody > tr > td:nth-child(1)"), secondModulesName));

        String moduleNameInViewWindow = editModulePage.getModuleNameInViewWindow();
        String lastModuleUpdateDateAndTimeBeforeEdit = editModulePage.getLastModuleUpdateDateAndTimeText();
        Assertions.assertEquals(secondModulesName, moduleNameInViewWindow, "Module names do not match");

        editModulePage.clickRedaguotiModuleButton();
        Thread.sleep(2000);

        editModulePage.clearModuleNameInEditWindow();
        editModulePage.sendUpdatedModuleName(moduleNameInViewWindow + "_edit");
        editModulePage.clickAtsauktiModuleEditButton();
        Thread.sleep(2000);

        String displayedModuleName = editModulePage.getModuleNameInViewWindow();
        String lastModuleUpdateDateAndTimeAfterEdit = editModulePage.getLastModuleUpdateDateAndTimeText();

        Assertions.assertEquals(displayedModuleName, moduleNameInViewWindow, "Module names do not match");
        Assertions.assertEquals(lastModuleUpdateDateAndTimeBeforeEdit, lastModuleUpdateDateAndTimeAfterEdit, "Last update date and time do not match after cancelling the update");

    }


    /**
     * User can update module name
     * - open Moduliai page
     * - click to view second module information
     * - check if module name in the 'view module' page is the same as second module's name
     * - click Redaguoti button
     * - write updated module name
     * - click Atnaujinti button
     * - check if last update date and time are updated to current date and time when the update is made
     * - check if the module name is updated
     *
     * @throws InterruptedException
     */
    @Test
    public void TestUserCanUpdateModuleInformation() throws InterruptedException {

        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        editModulePage = new EditModulePage(driver);

        mainPage.clickTvarkytiDuomenisItem();

        WaitUtils.waitForList(driver);

        String secondModulesName = modulesPage.getSecondModulesName();
        modulesPage.clickToViewSecondModuleInTheList();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#segment > div > table:nth-child(1) > tbody > tr > td:nth-child(1)"), secondModulesName));

        String moduleNameInViewWindow = editModulePage.getModuleNameInViewWindow();
        String updatedModuleName = secondModulesName + "_edit";

        Assertions.assertEquals(secondModulesName, moduleNameInViewWindow, "Module names do not match");
        editModulePage.clickRedaguotiModuleButton();
        Thread.sleep(2000);
        editModulePage.clearModuleNameInEditWindow();
        editModulePage.sendUpdatedModuleName(updatedModuleName);

        Thread.sleep(2000);
        editModulePage.clickAtnaujintiModuleButton();

        Thread.sleep(2000);

        String displayedModuleNameAfterUpdate = editModulePage.getModuleNameInViewWindow();
        String updateModuleDate = LocalDate.now().toString();
        String lastModuleUpdateDateAndTimeAfterEdit = editModulePage.getLastModuleUpdateDateAndTimeText().substring(0, 10);

        Assertions.assertEquals(updatedModuleName, displayedModuleNameAfterUpdate, "Module names do not match");
        Assertions.assertEquals(updateModuleDate, lastModuleUpdateDateAndTimeAfterEdit, "Last update date and time do not match after cancelling the update");

    }

}
