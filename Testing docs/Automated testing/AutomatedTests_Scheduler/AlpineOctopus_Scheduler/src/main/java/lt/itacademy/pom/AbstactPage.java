package lt.itacademy.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AbstactPage {

    protected final WebDriver driver;

    public AbstactPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
