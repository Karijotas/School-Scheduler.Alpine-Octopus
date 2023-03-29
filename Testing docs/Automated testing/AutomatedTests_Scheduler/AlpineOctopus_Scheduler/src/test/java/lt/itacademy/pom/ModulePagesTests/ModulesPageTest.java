package lt.itacademy.pom.ModulePagesTests;

import lt.itacademy.ModulePages.ArchiveModulePage;
import lt.itacademy.ModulePages.ModulesPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ModulesPageTest extends BaseTest {

    MainPage mainPage;
    ModulesPage modulesPage;
    ArchiveModulePage archiveModulePage;


    /**
     * Test Scenario: Check modules archive functionality
     */

    /**
     * User cancels module archive
     * - open Moduliai page
     * - click archive button for selected module
     * - check if warning message appears
     * - click Grizti atgal
     * - module should remain in the modules list
     *
     */
    @Test
    @Tag("regression")
    public void TestUserDoesNotArchiveModule() {
        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        archiveModulePage = new ArchiveModulePage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        WaitUtils.waitForList(driver);
        String secondModuleName = modulesPage.getSecondModulesName();
        modulesPage.clickToArchiveSecondModuleButton();

        String warningMessage = "Ar tikrai norite perkelti į archyvą?";

        Assertions.assertEquals(warningMessage, modulesPage.getWarningMessageText(), "Messages do not match");
        WaitUtils.waitForList(driver);
        modulesPage.clickGriztiAtgalFromWarningMessageButton();
        String expectedModuleName = modulesPage.getSecondModulesName();
        Assertions.assertEquals(expectedModuleName, secondModuleName, "Module names do not match");

    }

}
