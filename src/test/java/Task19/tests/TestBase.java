package Task19.tests;

import Task19.app.Application;
import org.junit.After;
import org.junit.Before;

public class TestBase {

    public Application app;

    @Before
    public void start() {
        app = new Application();
    }

    @After
    public void stop(){
        app.quit();
        app = null;
    }
}
