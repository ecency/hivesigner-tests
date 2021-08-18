package selenium.handlers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LocalStorageHandler {
    public WebDriver driver;


    public LocalStorageHandler(WebDriver driver) {
        this.driver = driver;
    }
    public void clearLocalStorage() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(String.format("window.localStorage.clear();"));
    }
}
