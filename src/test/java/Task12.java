import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Task12 {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void addNewProduct() {
        Random rand = new Random();
        Integer n = rand.nextInt(99999) + 1;
        String code = n.toString();
        String name = "New duck " + code;

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.xpath("//*[@id='box-apps-menu']//*[contains(text(),'Catalog')]")).click();
        driver.findElement(By.xpath("//*[@id='content']//*[@class='button' and contains(text(),'Product')]")).click();

        /*General tab*/
        /*Status*/
        WebElement status = driver.findElement(By.cssSelector("#tab-general tr label>input[value='1']"));
        System.out.println();
        if (status.getAttribute("checked") == null) {
            status.click();
        }

        /*Name*/
        driver.findElement(By.cssSelector("#tab-general input[name='name\\[en\\]']")).sendKeys(name);

        /*Code*/
        driver.findElement(By.cssSelector("#tab-general input[name='code']")).sendKeys(code);

        /*Categories*/
        driver.findElement(By.cssSelector("#tab-general input[data-name='Rubber Ducks']")).click();

        /*Default Category*/
        WebElement category = driver.findElement(By.cssSelector("select[name='default_category_id']"));
        Select categorySelect = new Select(category);
        categorySelect.selectByIndex(1);

        /*Product Groups*/
        driver.findElement(By.xpath("//td[contains(text(),'Female')]/../td/input")).click();
        driver.findElement(By.xpath("//td[contains(text(),'Male')]/../td/input")).click();

        /*Quantity*/
        WebElement quantity = driver.findElement(By.cssSelector("input[name='quantity']"));
        quantity.clear();
        quantity.sendKeys("85");

        /*Upload Images*/
        String imgPath = System.getProperty("user.dir") + "\\src\\source\\duck.jpg";
        WebElement img = driver.findElement(By.cssSelector("input[name='new_images\\[\\]']"));
        img.sendKeys(imgPath);

        /*Date Valid From*/
        driver.findElement(By.cssSelector("input[name='date_valid_from']")).sendKeys("15.04.2018");

        /*Date Valid To*/
        driver.findElement(By.cssSelector("input[name='date_valid_to']")).sendKeys("16.09.2018");

        /*
        Information tab
        */
        driver.findElement(By.xpath("//div[@class='tabs']//li/a[contains(text(),'Information')]")).click();

        /*Manufacturer*/
        WebElement manufacturer = driver.findElement(By.cssSelector("select[name='manufacturer_id']"));
        Select manufSelect = new Select(manufacturer);
        manufSelect.selectByIndex(1);

        /*Keywords*/
        driver.findElement(By.cssSelector("input[name='keywords']")).sendKeys("new yellow duck");

        /*Short Description*/
        driver.findElement(By.cssSelector("input[name='short_description\\[en\\]']")).sendKeys("new yellow duck");

        /*Description*/
        String descr = "It is a new yellow duck added by admin (code " + code + ")";
        driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys(descr);

        /*Head Title*/
        String title = "Yellow duck " + code;
        driver.findElement(By.cssSelector("input[name='head_title\\[en\\]']")).sendKeys(title);

        /*Meta Description*/
        driver.findElement(By.cssSelector("input[name='meta_description\\[en\\]']")).sendKeys(code);

        /*
        Prices tab
        */
        driver.findElement(By.xpath("//div[@class='tabs']//li/a[contains(text(),'Prices')]")).click();

        /*Purchase Price*/
        WebElement price = driver.findElement(By.cssSelector("input[name='purchase_price']"));
        price.clear();
        price.sendKeys("8,56");
        WebElement currency = driver.findElement(By.cssSelector("select[name='purchase_price_currency_code']"));
        Select currencySelect = new Select(currency);
        currencySelect.selectByValue("USD");

        /*Save new duck and check in root*/
        driver.findElement(By.cssSelector("button[type='submit'][name='save']")).click();
        List<WebElement> ducks = driver.findElements(By.cssSelector(".dataTable tr[class='row'] td:nth-child(3)>a"));
        int k=0;
        for (int i=0;i<ducks.size();i++){
            if (ducks.get(i).getAttribute("textContent").equals(name)){k++;}
        }
        Assert.assertEquals(k,1);
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
