import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Task13 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,15);
    }

    @Test
    public void workWithCart(){
        /*Add products*/
        for (int i=0;i<3;i++){
            driver.get("http://localhost/litecart/en/");

            WebElement product1 = driver.findElement(By.cssSelector("#box-most-popular li:nth-child(1) a[class='link']"));
            product1.click();
            wait.until(ExpectedConditions.stalenessOf(product1));

            String cartQty = driver.findElement(By.xpath("//div[@id='cart']//*[@class='quantity']")).getAttribute("textContent");
            String expectedQty = String.valueOf(Integer.parseInt(cartQty.toString()) + 1);
            if (isElementPresent("select[name='options\\[Size\\]']"))
            {
                WebElement size = driver.findElement(By.cssSelector("select[name='options\\[Size\\]']"));
                Select sizeSelect = new Select(size);
                sizeSelect.selectByIndex(1);
            }
            driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
            wait.until(ExpectedConditions.textToBe(By.xpath("//div[@id='cart']//*[@class='quantity']"), expectedQty));
        }

        driver.findElement(By.xpath("//a[@class='link' and contains(text(),'Checkout')]")).click();

        /*Qty and and name of unique products*/
        List<WebElement> products = driver.findElements(By.cssSelector("#box-checkout-cart .items li"));
        int[] productQty = new int[products.size()];
        String[] productName = new String[products.size()];
        for (int i=0;i<products.size();i++){
            int j=i+1;
            productQty[i] = Integer.parseInt(products.get(i).findElement(By.cssSelector("input[name='quantity']")).getAttribute("value"));
            productName[i] = products.get(i).findElement(By.xpath("..//li[" + j + "]//div//a")).getAttribute("textContent");
        }

        /*Remove each unique product*/
        int i=products.size();
        int k=0;
        while (i!=0){
            WebElement cartProduct = driver.findElement(By.cssSelector("#box-checkout-cart .items li:nth-child(1)"));
            WebElement tableRow = driver.findElement(By.xpath("//*[contains(@class,'dataTable')]//tr[2]//td[1][contains(text(),'" + productQty[k] + "')]/../td[2][contains(text(),'" + productName[k] + "')]/.."));

            if (i == products.size() && products.size()!=1){
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout-cart .items li:nth-child(1) button[name='remove_cart_item']")));
                    cartProduct.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
                }
                catch (Exception ex){
                    wait.until(ExpectedConditions.attributeContains(driver.findElement(By.cssSelector("#box-checkout-cart .items")), "style", "margin-left: 0px"));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout-cart .items li:nth-child(1) button[name='remove_cart_item']")));
                    cartProduct.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
                }
                wait.until(ExpectedConditions.stalenessOf(tableRow));
                System.out.println(productQty[k] + " product(s) of " + productName[k] + " have(has) been removed");
            }
            else{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout-cart .items li:nth-child(1) button[name='remove_cart_item']")));
                cartProduct.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
                wait.until(ExpectedConditions.stalenessOf(tableRow));
                System.out.println(productQty[k] + " product(s) of " + productName[k] + " have(has) been removed");
            }
            i--;
            k++;
        }
    }

    private Boolean isElementPresent(String locator){
        if (driver.findElements(By.cssSelector(locator)).size()>0) return true;
        else return false;
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
