package org.example;

import helper.ElementHelper;
import org.junit.Assert;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;



public class StepImplementation {

    public static WebDriver driver;

    @Step("Go to <url>")
    public void goToUrl(String url) {
        driver.navigate().to(url);
    }

    @Step({"Check <url> of the page"})
    public void checkUrl(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.urlToBe(url));

        } catch (Exception e) {
            Assert.assertTrue("This url " + url + " is wrong. Url: " + driver.getCurrentUrl(), false);
        }
    }

    @Step({"Click element with key <key>"})
    public void clickElement(String key) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By byElement = ElementHelper.getElementInfoToBy(key);
        wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
        driver.findElement(byElement).click();
    }

    @Step({"Is there an element with a <key> ?"})
    public void checkElement(String key) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            By byElement = ElementHelper.getElementInfoToBy(key);
            wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
        } catch (Exception e) {
            Assert.assertTrue("Element bulunamadı.", false);
        }
    }
    @Step("Is the text value of the element with <key> equal to <text> ?")
    public void checkText(String key, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            By byElement = ElementHelper.getElementInfoToBy(key);
            wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
            String text1 = driver.findElement(byElement).getText();
            Assert.assertSame(text1,text);
        } catch (Exception e) {
            Assert.assertTrue("Element bulunamadı.", false);
        }

    }
    @Step({"Click on the element with <key> equal to <text>"})
    public void findElementWithTextsAndClick(String key, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String elementText = null;
        boolean flag = true;
        int count = 1;
        while (flag) {
            try {
                By byElement = ElementHelper.getElementInfoToBy(key);
                wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
                List<WebElement> elements = driver.findElements(byElement);
                for (WebElement element : elements) {
                    swipeToElement(element);
                    if (!element.isDisplayed())
                        wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
                    elementText = element.getText();
                    element.click();
                    break;

                }
                flag = false;
                break;
            } catch (StaleElementReferenceException s) {
                count = count + 1;
                scrollDown();
                if (text.equals(elementText)) {
                    break;
                }
            }
        }
    }

    @Step({"New page opens."})
    public void navigateWindow() {
        try {
            String winHandleBefore = driver.getWindowHandle();
            Set<String> winHandles = driver.getWindowHandles();
            for (String handle : winHandles) {
                if (!handle.equals(winHandleBefore)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    public void scrollDown() {
        try {
            int i = 0;
            for (; i <= 30; i++) {
                ((JavascriptExecutor) driver).executeScript(("window.scrollBy(0," + i + ")"), "");
            }
            for (; i > 0; i--) {
                ((JavascriptExecutor) driver).executeScript(("window.scrollBy(0," + i + ")"), "");
            }
        } catch (WebDriverException wde) {
        } catch (Exception e) {
        }
    }

    public void swipeToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 25; i++) {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            js.executeScript("arguments[0].scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"center\"});", element);
        }
    }

}
