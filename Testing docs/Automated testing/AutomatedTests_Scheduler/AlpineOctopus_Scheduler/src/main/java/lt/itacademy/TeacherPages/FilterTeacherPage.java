package lt.itacademy.TeacherPages;

import lt.itacademy.pom.AbstactPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FilterTeacherPage extends AbstactPage {

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/div[2]/input")
    private WebElement filtruotiPagalDalykus;

    @FindBy(xpath = "/html/body/div/div/div[2]/div[2]/div/div/div[1]/input")
    private WebElement filtruotiPagalVarda;


    public FilterTeacherPage(WebDriver driver) {
        super(driver);
    }

    public void sendFilterBySubject(String filterBySubject) {
        filtruotiPagalDalykus.sendKeys(filterBySubject);
    }

    public void sendFilterByName(String filterByName) {
        filtruotiPagalVarda.sendKeys(filterByName);
    }
}
