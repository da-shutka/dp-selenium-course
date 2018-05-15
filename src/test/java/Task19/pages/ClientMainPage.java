package Task19.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClientMainPage extends Page {

    public ClientMainPage(WebDriver driver) {super(driver);}
    public void open() { driver.get("http://localhost/litecart/en/"); }
    public void checkMostPopularProducts(){
        Assert.assertNotEquals(0,driver.findElements(By.cssSelector("#box-most-popular ul.products>li")).size());
    }
    public void openProduct(){
        WebElement product = driver.findElement(By.cssSelector("#box-most-popular li:nth-child(1) a[class='link']"));
        product.click();
        wait.until(ExpectedConditions.stalenessOf(product));
    }
    public int getCurrentCartQty(){
        return Integer.parseInt(driver.findElement(By.xpath("//div[@id='cart']//*[@class='quantity']")).getAttribute("textContent"));
    }
}
