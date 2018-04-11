import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class task3 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 1000);
    }

    @Test
    public void loginScript(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Catalog')]")).click();
        driver.findElement(By.xpath("//i[contains(@class,'fa fa-home fa-lg')]")).click();
        driver.findElement(By.xpath("//a[@href='https://www.litecart.net/addons/156/hide-prices-from-guests']")).click();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}