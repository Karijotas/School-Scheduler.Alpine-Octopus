package lt.itacademy.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class WaitUtils {

    public static void waitForSubjectInEditView(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"subjects\"]/table/tbody/tr[3]/td[1]")));
    }

    public void waitForFirstGroupInTheList(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table > tbody > tr:first-child > td:first-child")));
    }

    public void waitForDalykai(){}

    public static void waitForList(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody > tr > td")),
                ExpectedConditions.textMatches(By.cssSelector("tbody > tr > td"), Pattern.compile(".+"))));
    }

    public static void waitForJs(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(dr -> ((Long) ((JavascriptExecutor) dr)
                .executeScript("return jQuery.active") == 0));
    }
}
