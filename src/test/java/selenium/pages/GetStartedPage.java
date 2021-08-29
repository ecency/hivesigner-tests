package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.ConfProperties;

public class GetStartedPage {
    private WebDriver driver;

    public GetStartedPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static GetStartedPage using(WebDriver driver) {
        return new GetStartedPage(driver);
    }

    public GetStartedPage navigateToPage() {
        driver.get(ConfProperties.getProperty("getStartedPageUrl"));
        return this;
    }

    @FindBy(xpath = "//div/div[@class='flex justify-center xl:justify-start']/*")
    private WebElement getStartedBtn;

    public GetStartedPage getStartedBtnClick() {
        this.getStartedBtn.click();
        System.out.println("Click on 'Get started' button...");
        return this;
    }

    public String getPageUrl() {
        System.out.println("Check that current page url is correct...");
        return driver.getCurrentUrl();
    }

    public GetStartedPage isPageLoaded() {
        this.getStartedBtn.isDisplayed();
        System.out.println("Get started page is loaded...");
        return this;
    }
}