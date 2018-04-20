import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class Task8 {
    public WebDriver driver;
    //public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }

    static Boolean isStickerPresent (WebElement product){
        try{
            product.findElement(By.cssSelector(".sticker"));
            return true;
        }
        catch(NoSuchElementException e){return false;}
    }

    static int areMultipleSrickers (WebElement product){
        return product.findElements(By.cssSelector(".sticker")).size();
    }

    @Test
    public void checkStickersLitecart(){
        driver.get("http://localhost/litecart");
        List<WebElement> products = driver.findElements(By.cssSelector("ul.products li"));
        Integer productsNumber = products.size();

        for (Integer i=0; i<productsNumber; i++){
            String productName = products.get(i).findElement(By.cssSelector(".name")).getAttribute("innerText");
            assertTrue(isStickerPresent(products.get(i)));
            assertEquals("Stickers count error for product " + productName,1, areMultipleSrickers(products.get(i)));
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
