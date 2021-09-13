package selenium.handlers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

import java.util.Calendar;
import java.util.Date;

public class JsCodeHandler {
    public WebDriver driver;

    public JsCodeHandler(WebDriver driver) {
        this.driver = driver;
    }

    public void clearLocalStorage() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(String.format("window.localStorage.clear();"));
    }

    public void putLog(String text) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("console.log('" + text + "')");
    }

    public LogEntries getLogs() {
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        for (LogEntry logEntry : logEntries) {
            System.out.println(logEntry.getMessage());
        }
        return logEntries;
    }

    public Boolean isLineInLogs(LogEntries logEntries, String expectedLine) {
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getMessage().contains(expectedLine)) {
                return true;
            }
        }
        return false;
    }

    public long getNextDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 3);
        return cal.getTime().getTime();
    }

    public void increaseDateJs() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        long nextTimeStamp = getNextDate();
        jse.executeScript("console.log('\"var dateYouWant =\" + nextTimeStamp + \"; Date.prototype.getTime = function() { return dateYouWant; };\"')");
        jse.executeScript("var dateYouWant =" + nextTimeStamp + "; Date.prototype.getTime = function() { return dateYouWant; };");
    }
}
