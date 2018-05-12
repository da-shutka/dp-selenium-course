import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task17 {
    public WebDriver driver;

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkProductsPageLogs(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        List<WebElement> prodCategory1 = driver.findElements(By.xpath("//*[@class='dataTable']//td[3]/a[contains(@href,'category_id=1')]"));
        for (int i=0;i<prodCategory1.size();i++){
            WebElement element = driver.findElements(By.xpath("//*[@class='dataTable']//td[3]/a[contains(@href,'category_id=1')]")).get(i);
            element.click();
            List<LogEntry> logs = this.driver.manage().logs().get("browser").getAll();
            Assert.assertEquals(logs.size(),0, logs.toString());
            driver.findElement(By.cssSelector("button[name='cancel']")).click();
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
