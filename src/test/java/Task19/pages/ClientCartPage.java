package Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ClientCartPage extends Page {
    public ClientCartPage(WebDriver driver) {super(driver);}
    public List<WebElement> getProducts() throws Exception {
        List<WebElement> products = driver.findElements(By.cssSelector("#box-checkout-cart .items li"));
        if (products.size()<1){
            throw new Exception("Whoops! Cart is broken: " + products.size() + " product in cart");
        }
        return products;
    }
    public String[] getUniqueProductsNames(List<WebElement> products){
        String[] uniqueProductName = new String[products.size()];
        for (int i=0;i<products.size();i++){
            int j=i+1;
            uniqueProductName[i] = products.get(i).findElement(By.xpath("..//li[" + j + "]//div//a")).getAttribute("textContent");
        }
        return uniqueProductName;
    }

    public int[] getUniqueProductsQty(List<WebElement> products){
        int[] uniqueProductQty = new int[products.size()];
        for (int i=0;i<products.size();i++){
            uniqueProductQty[i] = Integer.parseInt(products.get(i).findElement(By.cssSelector("input[name='quantity']")).getAttribute("value"));
        }
        return uniqueProductQty;
    }

    public void simpleRemove(WebElement product, WebElement row){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout-cart .items li:nth-child(1) button[name='remove_cart_item']")));
        removeAndCheckThatRemoved(product,row);
    }

    public void hardRemove(WebElement product, WebElement row){
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.cssSelector("#box-checkout-cart .items")), "style", "margin-left: 0px"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#box-checkout-cart .items li:nth-child(1) button[name='remove_cart_item']")));
        removeAndCheckThatRemoved(product,row);
    }

    private void removeAndCheckThatRemoved(WebElement productInBox, WebElement productInTable){
        productInBox.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
        wait.until(ExpectedConditions.stalenessOf(productInTable));
    }
}
