package lt.itacademy.pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    public static WebDriver driver;

    @BeforeEach
    public void createDriverOpenDriver(){
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.manage().window().maximize();
        //driver.get("http://localhost:3000/");
        driver.get("https://tomcat.akademijait.vtmc.lt/alpine/");
    }


    @AfterEach
    public void quitBrowser(){
       //driver.quit();
    }

}
