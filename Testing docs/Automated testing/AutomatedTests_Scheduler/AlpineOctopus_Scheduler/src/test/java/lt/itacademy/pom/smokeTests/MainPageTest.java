package lt.itacademy.pom.smokeTests;

import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MainPageTest extends BaseTest {

    MainPage mainPage;

    /**
     * Test Scenario: Check if all main pages open
     */

    @Test
    @Tag("smoke")
    public void TestDalykaiPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Dalykai page opens
        mainPage.clickDalykaiTabItem();
        String expectedDalykaiTitle = "Dalyko pavadinimas";
        String displayedDalykaiTitle = mainPage.getPageTitle();
        Assertions.assertEquals(expectedDalykaiTitle, displayedDalykaiTitle, "Titles do not match");
    }

    @Test
    @Tag("smoke")
    public void TestMokytoajaiPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Mokytojai page opens
        mainPage.clickMokytojaiTabItem();
        String expectedMokytojaiTitle = "Mokytojo vardas";
        String displayedMokytojaiTitle = mainPage.getPageTitle();
        Assertions.assertEquals(expectedMokytojaiTitle, displayedMokytojaiTitle, "Titles do not match");
    }

    @Test
    @Tag("smoke")
    public void TestGrupesPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Grupes page opens
        mainPage.clickGrupesTabItem();
        String expectedGrupesTitle = "Grupės pavadinimas \"Teams\"";
        String displayedGrupesTitle = mainPage.getPageTitle();
        Assertions.assertEquals(expectedGrupesTitle, displayedGrupesTitle, "Titles do not match");
    }

    @Test
    @Tag("smoke")
    public void TestPamainosPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Pamainos page opens
        mainPage.clickPamainosTabItem();
        String expectedPamainosTitle = "Pamainos pavadinimas";
        String displayedPamainosTitle = mainPage.getPageTitle();
        Assertions.assertEquals(expectedPamainosTitle, displayedPamainosTitle, "Titles do not match");
    }

    @Test
    @Tag("smoke")
    public void TestKabinetaiPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Kabinetai page opens
        mainPage.clickKabinetaiTabItem();
        String expectedKabinetaiTitle = "Kabineto pavadinimas";
        String displayedKabinetaiTitle = mainPage.getPageTitle();
        Assertions.assertEquals(expectedKabinetaiTitle, displayedKabinetaiTitle, "Titles do not match");
    }

    @Test
    @Tag("smoke")
    public void TestProgramosPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Programos page opens
        mainPage.clickProgramosTabItem();
        String expectedProgramosTitle = "Programos pavadinimas";
        String displayedProgramosTitle = mainPage.getPageTitle();
        Assertions.assertEquals(expectedProgramosTitle, displayedProgramosTitle, "Titles do not match");
    }

    @Test
    @Tag("smoke")
    public void TestModuliaiPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

        // check if Moduliai page opens
        mainPage.clickModuliaiTabItem();
        String expectedModuliaiText = "Modulio pavadinimas";
        String displayedModuliaiText = mainPage.getModuliaiDisplayedText();
        Assertions.assertEquals(expectedModuliaiText, displayedModuliaiText, "text does not match");
    }

    @Test
    @Tag("smoke")
    public void TestAtostogosPageOpens() {
        mainPage = new MainPage(driver);
        mainPage.clickTvarkytiDuomenisItem();

//        // check if Atostogos page opens
        mainPage.clickAtostogosTabItem();
        String expectedAtostogosText = "Atostogų pavadinimas";
        String displayedAtostogosText = mainPage.getPageTitle();
        Assertions.assertEquals(expectedAtostogosText, displayedAtostogosText, "text does not match");
    }

    @Test
    @Tag("smoke")
    public void TestTvarkarasciaiPageOpens(){
        mainPage = new MainPage(driver);

        mainPage.clickTvarkarasciaiItem();
        String expectedTitle = "Grupės/ tvarkaraščio pavadinimas";
        String displayedTitle = mainPage.getTvarkarasciaiTitleDisplayedText();
        Assertions.assertEquals(expectedTitle, displayedTitle, "Titles do not match");
    }



}
