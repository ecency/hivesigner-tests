package selenium.handlers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import selenium.ConfProperties;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotsHandler {
    public WebDriver driver;

    public ScreenshotsHandler(WebDriver driver) {
        this.driver = driver;
    }

    public void screenshotMaker() {
        Date dateNow = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yy_MM_dd_HH_mm_ss_Z");
        String fileName = format.format(dateNow) + ".png";

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File((ConfProperties.getProperty("SCREENSHOOT_PATH")) + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}