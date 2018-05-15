package Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ClientProductPage extends Page {
    public ClientProductPage(WebDriver driver) {super(driver);}
    public ClientProductPage setProductSize(){
        if (isElementPresent("select[name='options\\[Size\\]']"))
        {
            WebElement size = driver.findElement(By.cssSelector("select[name='options\\[Size\\]']"));
            Select sizeSelect = new Select(size);
            sizeSelect.selectByIndex(1);
        }
        return this;
    }
    private Boolean isElementPresent(String locator){
        return driver.findElements(By.cssSelector(locator)).size()>0;
    }
    public void addToCart(int expectedQty){
        driver.findElement(By.cssSelector("button[name='add_cart_product']")).click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//div[@id='cart']//*[@class='quantity']"), String.valueOf(expectedQty)));
    }
    public void openCart() {
        driver.findElement(By.xpath("//a[@class='link' and contains(text(),'Checkout')]")).click();
    }
}
