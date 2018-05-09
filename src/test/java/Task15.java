import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class Task15 {
    private WebDriver driverChrome;
    private WebDriver driverFirefox;
    private WebDriver driverIE;

    @Before
    public void start() throws MalformedURLException {
        driverChrome = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), DesiredCapabilities.chrome());
        driverFirefox = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), DesiredCapabilities.firefox());
        driverIE = new RemoteWebDriver(new URL("http://192.168.0.104:4444/wd/hub"), DesiredCapabilities.internetExplorer());
    }

    @Test
    public void myTest() {
        driverChrome.get("https://github.com");
        driverFirefox.get("https://github.com");
        driverIE.get("https://github.com");
    }

    @After
    public void stop(){
        driverChrome.quit();
        driverChrome = null;
        driverFirefox.quit();
        driverFirefox = null;
        driverIE.quit();
        driverIE = null;
    }
}
