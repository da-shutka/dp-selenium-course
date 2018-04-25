import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Task10 {
    public WebDriver driver;
    public WebDriverWait wait;

    @Parameters("browser")
    @BeforeClass
    public void start(String browser){
        if (browser.equalsIgnoreCase("chrome")){
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.firefox.marionette", "C:\\Tools\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else if (browser.equalsIgnoreCase("ie")){
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability("ignoreZoomSetting", true);
            driver = new InternetExplorerDriver(capabilities);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkYellowDuck() {
        driver.get("http://localhost/litecart/en/");
        String[] mainRegPriceData = new String[5];
        String[] mainCampPriceData = new String[5];
        String[] duckRegPriceData = new String[5];
        String[] duckCampPriceData = new String[5];

        /*main page*/
        WebElement mainDuck = driver.findElement(By.cssSelector("#box-campaigns li:nth-child(1)"));
        String nameMain = mainDuck.findElement(By.cssSelector(".name")).getAttribute("textContent");
        WebElement mainRegPrice = mainDuck.findElement(By.cssSelector(".price-wrapper .regular-price"));
        WebElement mainCampPrice = mainDuck.findElement(By.cssSelector(".price-wrapper .campaign-price"));
        mainRegPriceData = getPriceInfo(mainRegPrice);
        mainCampPriceData = getPriceInfo(mainCampPrice);
        /*main page*/

        //driver.findElement(By.cssSelector("#box-campaigns li:nth-child(1) a.link")).click();
        ((JavascriptExecutor)driver).executeScript("document.querySelector(\"#box-campaigns li:nth-child(1) a.link\").click();");

        /*duck page*/
        WebElement duck = driver.findElement(By.cssSelector("#box-product"));
        String duckName = duck.findElement(By.cssSelector("h1")).getAttribute("textContent");
        WebElement duckRegPrice = duck.findElement(By.cssSelector(".price-wrapper .regular-price"));
        WebElement duckCampPrice = duck.findElement(By.cssSelector(".price-wrapper .campaign-price"));
        duckRegPriceData = getPriceInfo(duckRegPrice);
        duckCampPriceData = getPriceInfo(duckCampPrice);
        /*duck page*/

        /*compare duck names*/
        Assert.assertEquals( nameMain, duckName,"Names are not equal: " + nameMain + " and " + duckName);

        /*compare regular prices*/
        Assert.assertEquals(mainRegPriceData[0],duckRegPriceData[0],"Regular prices are not equal: " + mainRegPriceData[0] + " and " + duckRegPriceData[0]);

        /*compare campaign prices*/
        Assert.assertEquals(mainCampPriceData[0],duckCampPriceData[0],"Campaign prices are not equal: " + mainCampPriceData[0] + " and " + duckCampPriceData[0]);

        /*check line-through*/
        Assert.assertEquals("s",mainRegPriceData[4],"Regular price is not crossed (main page): ");
        Assert.assertEquals("s",duckRegPriceData[4],"Regular price is not crossed (duck page): ");

        /*check grey color*/
        Assert.assertTrue(isColorGrey(mainRegPriceData[3]),"Regular price is not grey (main page): ");
        Assert.assertTrue(isColorGrey(duckRegPriceData[3]),"Regular price is not grey (duck page): ");

        /*check bold*/
        Assert.assertEquals("strong", mainCampPriceData[1],"Campaign price is not bold (main page): ");
        Assert.assertEquals("strong", duckCampPriceData[1],"Campaign price is not bold (duck page): ");

        /*check red color*/
        Assert.assertTrue(isColorRed(mainCampPriceData[3]),"Campaign price is not red (main page): ");
        Assert.assertTrue(isColorRed(duckCampPriceData[3]),"Campaign price is not red (duck page): ");

        /*compare regular and campaign price font*/
        Assert.assertTrue(isValue1LessValue2(mainRegPriceData[2],mainCampPriceData[2]),"Regular price is not less than campaign price (main page)!");
        Assert.assertTrue(isValue1LessValue2(duckRegPriceData[2],duckCampPriceData[2]),"Regular price is not less than campaign price (duck page)!");
    }

    static String[] getPriceInfo(WebElement price){
        String[] priceData = new String[5];
        String priceValue; String priceWeight; String priceSize; String priceColor; String priceDecor;

        priceValue = price.getAttribute("textContent");
          priceValue = priceValue.replaceAll("\\$","");
        priceWeight = price.getAttribute("localName");
        priceSize = price.getCssValue("font-size");
          priceSize = priceSize.replaceAll("px","");
        priceColor = price.getCssValue("color");
          priceColor = priceColor.replaceAll("rgba\\(|rgb\\(|\\)","");
        priceDecor = price.getAttribute("localName");

        priceData[0] = priceValue;
        priceData[1] = priceWeight;
        priceData[2] = priceSize;
        priceData[3] = priceColor;
        priceData[4] = priceDecor;

        return priceData;
    }

    static Boolean isValue1LessValue2(String value1, String value2){
        if (Float.parseFloat(value1) < Float.parseFloat(value2)) {return true;}
        else {return false;}
    }

    static Boolean isColorGrey(String color){
        String[] colorValue = color.split(", ");
        if (colorValue[0].equals(colorValue[1]) && colorValue[1].equals(colorValue[2])){return true;}
        else {return false;}
    }

    static Boolean isColorRed(String color){
        String[] colorValue = color.split(", ");
        if (colorValue[0]!="0" && colorValue[1].equals("0") && colorValue[2].equals("0")){return true;}
        else {return false;}
    }

    @AfterClass
    public void stop(){
        driver.quit();
        driver = null;
    }
}
