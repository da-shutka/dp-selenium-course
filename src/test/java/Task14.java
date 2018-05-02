import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Task14 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,5);
    }

    @Test
    public void checkLinks(){
        Random rand = new Random();

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        WebElement country = driver.findElement(By.xpath("//ul[@id='box-apps-menu']/li//span[contains(text(),'Countries')]/.."));
        country.click();
        int countryCount = driver.findElements(By.cssSelector(".dataTable .row")).size();
        int n = rand.nextInt(countryCount)+1;

        driver.findElement(By.xpath("//table[@class='dataTable']//tr[@class='row'][" + n + "]/td[5]/a")).click();
        wait.until(ExpectedConditions.stalenessOf(country));

        List<WebElement> extLinks = driver.findElements(By.xpath("//*[contains(@class,'fa-external-link')]/.."));

        String originalWindow = driver.getWindowHandle();
        for (int i=0;i<extLinks.size();i++){
            Set<String> existingWindows = driver.getWindowHandles();
            extLinks.get(i).click();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows){
        return webDriver -> {
            Set<String> allWindows = webDriver.getWindowHandles();
            allWindows.removeAll(oldWindows);
            if (allWindows.size()>0){
                return allWindows.iterator().next();
            }
            else {return null;}
        };
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
