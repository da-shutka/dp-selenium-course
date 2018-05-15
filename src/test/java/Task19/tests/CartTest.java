package Task19.tests;

import org.junit.Test;

public class CartTest extends TestBase {

    @Test
    public void addAndRemoveProductsInCart() throws Exception {
        app.openMainPage().areProductsPresent();
        for (int i=0;i<3;i++){ app.addProductToCart(); }
        app.openCart();
        app.removeProductsFromCart();
    }
}
