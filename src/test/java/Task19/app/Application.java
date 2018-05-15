package Task19.app;

import Task19.pages.ClientCartPage;
import Task19.pages.ClientMainPage;
import Task19.pages.ClientProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Application{

    private WebDriver driver;
    private WebDriverWait wait;

    private ClientMainPage clientMainPage;
    private ClientCartPage clientCartPage;
    private ClientProductPage clientProductPage;

    public Application(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        clientMainPage = new ClientMainPage(driver);
        clientCartPage = new ClientCartPage(driver);
        clientProductPage = new ClientProductPage(driver);
    }

    public void quit(){ driver.quit(); }

    public Application openMainPage(){ clientMainPage.open(); return this; }

    public void openCart(){ clientProductPage.openCart(); }

    public void areProductsPresent(){ clientMainPage.checkMostPopularProducts(); }

    public void addProductToCart(){
        clientMainPage.open();
        int expectedQty = clientMainPage.getCurrentCartQty() + 1;
        clientMainPage.openProduct();
        clientProductPage.setProductSize().addToCart(expectedQty);
    }

    public void removeProductsFromCart() throws Exception {
        List<WebElement> productsInCart = clientCartPage.getProducts();
        String[] uniqueProductNames = clientCartPage.getUniqueProductsNames(productsInCart);
        int[] uniqueProductsQty = clientCartPage.getUniqueProductsQty(productsInCart);

        int i=productsInCart.size();
        int k=0;
        while (i!=0){
            WebElement cartProduct = clientCartPage.getCurrentProduct();
            WebElement tableRow = clientCartPage.getProductTableRow(uniqueProductsQty[k],uniqueProductNames[k]);

            if (i == productsInCart.size() && productsInCart.size()!=1){
                try {clientCartPage.simpleRemove(cartProduct, tableRow); }
                catch (Exception ex){clientCartPage.hardRemove(cartProduct,tableRow);}
            }
            else{clientCartPage.simpleRemove(cartProduct, tableRow); }
            i--;
            k++;
        }
    }
}