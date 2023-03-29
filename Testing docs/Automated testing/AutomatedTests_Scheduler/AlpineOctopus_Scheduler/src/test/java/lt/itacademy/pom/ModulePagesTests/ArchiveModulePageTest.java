package lt.itacademy.pom.ModulePagesTests;

import jdk.jfr.Category;
import lt.itacademy.ModulePages.ArchiveModulePage;
import lt.itacademy.ModulePages.ModulesPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ArchiveModulePageTest extends BaseTest {

    MainPage mainPage;
    ModulesPage modulesPage;
    ArchiveModulePage archiveModulePage;

    /**
     * Test Scenario: Check archive functionality
     */

    /**
     * User can archive module
     * - open modules list
     * - select a second module
     * - click 'archive' button
     * - click 'Taip'
     * - check if module is in the beginning of the archived modules list
     */

    @Test
    @Tag("regression")
    public void TestUserArchivesModuleTest(){
        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        archiveModulePage = new ArchiveModulePage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        WaitUtils.waitForList(driver);
        String secondModuleName = modulesPage.getSecondModulesName();
        modulesPage.clickToArchiveSecondModuleButton();

        String warningMessage = "Ar tikrai norite perkelti į archyvą?";

        Assertions.assertEquals(warningMessage, modulesPage.getWarningMessageText(), "Messages do not match");

        modulesPage.clickYesToArchiveModule();

        mainPage.clickArchiveButton();
        archiveModulePage.clickModuliuArchyvasTab();

        WaitUtils.waitForList(driver);
        String latestArchivedModulename = archiveModulePage.getLatestArchivedModuleName();

        Assertions.assertEquals(latestArchivedModulename, secondModuleName, "Module names do not match");
    }


    /**
     * User can restore archived module
     * - open Archyvas, select Moduliu archyvas
     * - select module and click 'restore' button
     * - check if module appears in the beginning of the main modules list
     */
    @Test
    @Tag("regression")
    public void TestUserRestoresArchivedModule(){
        mainPage = new MainPage(driver);
        archiveModulePage = new ArchiveModulePage(driver);
        modulesPage = new ModulesPage(driver);

        mainPage.clickTvarkytiDuomenisItem();

        mainPage.clickArchiveButton();
        archiveModulePage.clickModuliuArchyvasTab();

        WaitUtils.waitForList(driver);
        String latestFirstModuleNameInArchivedList = archiveModulePage.getLatestArchivedModuleName();

        archiveModulePage.clickToRestoreFirstArchivedModuleInList();
        modulesPage.openModuliaiTab();

        WaitUtils.waitForList(driver);
        String firstModuleNameInTheList = modulesPage.getFirstModulesName();

        Assertions.assertEquals(firstModuleNameInTheList, latestFirstModuleNameInArchivedList, "Modules name do not match");

    }
}
