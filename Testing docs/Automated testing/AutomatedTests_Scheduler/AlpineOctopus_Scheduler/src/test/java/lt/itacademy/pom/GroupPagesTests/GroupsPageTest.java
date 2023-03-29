package lt.itacademy.pom.GroupPagesTests;

import lt.itacademy.GroupPages.GroupsPage;
import lt.itacademy.pom.BaseTest;
import lt.itacademy.smokeTestsPage.MainPage;
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

public class GroupsPageTest extends BaseTest {

    MainPage mainPage;
    GroupsPage groupsPage;

    /**
     * Test Scenario: Check filtering functionality for group list
     */

    /**
     * User can filter group list by name
     * - open group list page
     * - send text to filter box
     * - check if filtered names match filtering text
     *
     * @param groupFromFile
     *
     */
    @ParameterizedTest
    @Tag("regression")
    @CsvFileSource(resources = "/groupsToFilter.txt")
    public void TestUserCanFilterGroupByName(String groupFromFile) {

        mainPage = new MainPage(driver);
        groupsPage = new GroupsPage(driver);

        mainPage.clickTvarkytiDuomenisItem();
        mainPage.clickGrupesTabItem();

        WaitUtils.waitForList(driver);
        WebElement initialFirstRow = driver.findElement(By.cssSelector("table > tbody > tr:first-child > td:first-child"));
        groupsPage.sendTextToFilterGroupByName(groupFromFile);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(initialFirstRow));

        String filteredGroupName = groupsPage.getFirstGroupInTheListName();


        Assertions.assertTrue(filteredGroupName.contains(groupFromFile), "Filter did not find group name " + groupFromFile);


    }


}
