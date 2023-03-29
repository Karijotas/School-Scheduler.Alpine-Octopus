package lt.itacademy.pom.ModulePagesTests;

import lt.itacademy.ModulePages.CreateModulePage;
import lt.itacademy.ModulePages.EditModulePage;
import lt.itacademy.ModulePages.ModulesPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import lt.itacademy.utils.RandomUtils;
import lt.itacademy.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateModulePageTest extends BaseTest {


    MainPage mainPage;
    ModulesPage modulesPage;
    CreateModulePage createModulePage;
    EditModulePage editModulePage;

     private String newModuleDescription = "Skandinavų karai ir mūšiai";

    /**
     * Test Scenario: Check new module creation functionality
     */

    /**
     * User can create new module
     * - open Moduliai page
     * - click Kurti nauja moduli button
     * - check if Sukurti button is disabled
     * - fill in all information
     * - click Sukurti button
     * - check if new module appears in the beginning of the modules list
     * -
     * @param moduleFromFile
     */

    @ParameterizedTest
    @Tag("smoke")
    @CsvFileSource(resources = "/modulesToCreate.txt")
    public void UserCreatesNewModule(String moduleFromFile){

        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        createModulePage = new CreateModulePage(driver);
        editModulePage = new EditModulePage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();

        Boolean isSukurtiButtonDisabled = createModulePage.isSukurtiButtonDisabled();
        Assertions.assertFalse(isSukurtiButtonDisabled, "Sukurti button is enabled");

        String sentModuleName = moduleFromFile + RandomUtils.generateTimeStamp();
        createModulePage.sendNewModuleName(sentModuleName);

        createModulePage.sendNewModuleDescription(newModuleDescription);
        createModulePage.clickSukurtiNewModule();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#segment > div > table:nth-child(1) > tbody > tr > td:nth-child(1)"), sentModuleName));
        String displayedModuleNameInViewWindow = editModulePage.getModuleNameInViewWindow();

        WaitUtils.waitForList(driver);
        editModulePage.clickAtgalToModulesListButton();

        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id=\"modules\"]/table/tbody/tr[1]/td[1]"), displayedModuleNameInViewWindow));

        String firstModuleNameInTheList = modulesPage.getFirstModulesName();

        Assertions.assertEquals(displayedModuleNameInViewWindow, firstModuleNameInTheList, "Module names do not match");

    }

    /**
     * User cannot create a new module if name field is empty
     * - open Moduliai page, click Sukurti nauja moduli
     * - check if Sukurti Button is disabled
     * - check if warning message pops up
     */

    @Test
    @Tag("regression")
    public void UserCannotCreateNewModuleWithEmptyName(){
        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        createModulePage = new CreateModulePage(driver);
        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickKurtiNaujaKlasifikatoriu();
        createModulePage.clickModuleNameInCreateWindow();

        WebElement sukurtiModuleButton = driver.findElement(By.xpath("//button[text()=\"Sukurti\"]"));
        Assertions.assertFalse(sukurtiModuleButton.isEnabled(), "Sukurti button is displayed");

        createModulePage.clickModuleDescriptionInCreateWindow();

        String expectedWarningMessage = "Negali būti tuščias!";
        String displayedWarningMessage = createModulePage.getWarningMsgTextForBlankModuleName();

        Assertions.assertEquals(expectedWarningMessage, displayedWarningMessage, "Messages do not match");

    }


    /**
     * User starts creating new module and clicks Atsaukti button
     * - open Moduliai page
     * - click Sukurti nauja moduli
     * - fill in information
     * - click Atsaukti
     * - new module should not be shown in modules list
     */
    @Test
    @Tag("regression")
    public void UserDoesNotWantToCreateNewModuleTest(){
        mainPage = new MainPage(driver);
        modulesPage = new ModulesPage(driver);
        createModulePage = new CreateModulePage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        WaitUtils.waitForList(driver);
        String firstModuleNameInTheList = modulesPage.getFirstModulesName();

        mainPage.clickKurtiNaujaKlasifikatoriu();
        createModulePage.sendNewModuleName("newModuleName");
        createModulePage.sendNewModuleDescription(newModuleDescription);

        createModulePage.clickAtgalToModulesListButton();

        WaitUtils.waitForList(driver);
        String firstModuleNameInTheListAfterCancelCreation = modulesPage.getFirstModulesName();

        Assertions.assertEquals(firstModuleNameInTheList, firstModuleNameInTheListAfterCancelCreation, "Module names do not match");
    }




}
