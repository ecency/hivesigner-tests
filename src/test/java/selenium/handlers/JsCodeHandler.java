package selenium.handlers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

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

    public void getTimeZone(){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("console.log(new Date().toTimeString().slice(9));\n" +
                "console.log(Intl.DateTimeFormat().resolvedOptions().timeZone);\n" +
                "console.log(new Date().getTimezoneOffset() / -60);");
    }

    public void changeTimeZone(){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("console.log(");
    }

}
