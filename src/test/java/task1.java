import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class task1 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5000);
    }

    @Test
    public void myFirstTask(){
        driver.get("https://github.com");
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
