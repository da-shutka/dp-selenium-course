import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Task11 {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void registerUser(){
        Random rand = new Random();

        Integer n = rand.nextInt(99999)+1;
        Integer pass = rand.nextInt(999999999) + 1;

        String email = "petrova.tpu+" + n.toString() + "@gmail.com";
        String password = pass.toString();

        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.xpath("//*[@name='login_form']//tr//a")).click();

        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='firstname']")).sendKeys("Daria");
        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='lastname']")).sendKeys("Petrova");
        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='address1']")).sendKeys("6, Selenium Street");
        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='postcode']")).sendKeys("12345");
        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='city']")).sendKeys("Boston");

        WebElement country = driver.findElement(By.xpath("//select[@name='country_code']"));
        Select countrySelect = new Select(country);
        countrySelect.selectByVisibleText("United States");

        WebElement state = driver.findElement(By.xpath("//select[@name='zone_code']"));
        Select stateSelect = new Select(state);
        stateSelect.selectByVisibleText("Massachusetts");

        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='phone']")).sendKeys("+79879877898");

        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@name='customer_form']//td//*[@name='confirmed_password']")).sendKeys(password);

        new Actions(driver)
                .moveToElement(driver.findElement(By.xpath("//*[@name='create_account']")))
                .click()
                .pause(3)
                .perform();
        driver.findElement(By.xpath("//*[@id='box-account']//*[contains(text(),'Logout')]")).click();

        driver.findElement(By.xpath("//*[@name='login_form']//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//*[@name='login_form']//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@name='login']")).click();
        driver.findElement(By.xpath("//*[@id='box-account']//*[contains(text(),'Logout')]")).click();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
