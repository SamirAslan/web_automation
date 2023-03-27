package driver;

import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import org.openqa.selenium.WebDriver;

public class Driver {

    public static WebDriver driver ;

    @BeforeSuite
    public void initialzeDriver(){

        driver = DriverFactory.getDriver();
    }

    @AfterSuite
    public void closeDriver() {
        driver.close();
    }


}
