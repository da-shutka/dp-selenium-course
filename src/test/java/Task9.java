import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.Assert.*;

public class Task9 {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    public void start(){
        if (driver!=null){return;}
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {driver.quit(); driver=null;})
        );
    }

    @Test
    public void checkSortCountries(){
        //part A
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        WebElement table = driver.findElement(By.cssSelector(".dataTable"));
        List<WebElement> countryName = table.findElements(By.xpath(".//td[5]//a"));

        for (int i=1; i< countryName.size(); i++){
            String previousCountry = countryName.get(i-1).getAttribute("textContent");
            String currentCountry = countryName.get(i).getAttribute("textContent");
            assertTrue(previousCountry + " and " + currentCountry + " are not sorted!",areSorted(previousCountry, currentCountry));
        }

        //part B
        for (int i=1;i<=countryName.size();i++){
            WebElement countryRow = driver.findElement(By.xpath("//*[@class='dataTable']//tr[@class='row'][" + i + "]"));

            if (Integer.parseInt(countryRow.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent")) != 0){
                countryRow.findElement(By.cssSelector("a")).click();
                List<WebElement> zones = driver.findElements(By.xpath("//*[@id='table-zones']//td[3]//input[contains(@name,'zones')]//.."));

                for (int j=1; j< zones.size(); j++){
                    String previousZone = zones.get(j-1).getAttribute("textContent");
                    String currentZone = zones.get(j).getAttribute("textContent");
                    assertTrue(previousZone + " and " + currentZone + " are not sorted!",areSorted(previousZone, currentZone));
                }
                driver.findElement(By.cssSelector("[name=cancel]")).click();
            }
        }
    }

    @Test
    public void checkSortGeoZones(){
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        WebElement table = driver.findElement(By.cssSelector(".dataTable"));
        List<WebElement> countries = table.findElements(By.xpath(".//td[3]"));

        for (int i=1;i<=countries.size();i++){
            driver.findElement(By.xpath("//*[@class='dataTable']//tr[@class='row'][" + i + "]/td[3]/a")).click();
            List<WebElement> zones = driver.findElements(By.xpath("//*[@id='table-zones']//td[3]//option[@selected='selected']"));

            for (int j=1; j< zones.size(); j++){
                String previousZone = zones.get(j-1).getAttribute("textContent");
                String currentZone = zones.get(j).getAttribute("textContent");
                assertTrue(previousZone + " and " + currentZone + " are not sorted!",areSorted(previousZone, currentZone));
            }
            driver.findElement(By.cssSelector("[name=cancel]")).click();
        }
    }

    static Boolean areSorted (String previous, String current){
        if (current.compareTo(previous) > 0){ return true;}
        else {return false;}
    }
}
