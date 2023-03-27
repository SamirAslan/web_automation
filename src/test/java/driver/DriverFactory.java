package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverFactory {

    public static  WebDriver getDriver(){

        String browser = System.getenv("BROWSER");
        if (browser == null){
            return new ChromeDriver();
        }
        switch (browser){
            case "FIREFOX":
                return new FirefoxDriver();
            default:
                return new ChromeDriver();
        }

    }

}
