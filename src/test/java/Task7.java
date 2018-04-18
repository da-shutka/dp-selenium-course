import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task7 {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @Test
    public void reviewAdminSections(){
        driver.get("http://localhost/litecart/admin/");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        Integer menuSize = driver.findElements(By.cssSelector("#box-apps-menu li#app-")).size();

        for (Integer i=1; i<= menuSize; i++){
            WebElement menu = driver.findElement(By.cssSelector("#box-apps-menu"));
            menu.findElement(By.xpath("./li[" + i + "]")).click();
            driver.findElement(By.xpath("//h1"));

            Integer sectionSize = driver.findElements(By.cssSelector("#box-apps-menu > li:nth-child(" + i + ") li")).size();
            if (sectionSize!=0){
                for (Integer k=1; k<=sectionSize; k++){
                    driver.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//li[" + k + "]")).click();
                    driver.findElement(By.xpath("//h1"));
                }
            }
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}