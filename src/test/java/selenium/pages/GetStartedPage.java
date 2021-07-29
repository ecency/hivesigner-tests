package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GetStartedPage {
    public WebDriver driver;

    public GetStartedPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div/div[@class='flex justify-center xl:justify-start']/*")
    private WebElement getStartedBtn;

    public void getStartedBtnClick() {
        getStartedBtn.click();
    }

    public String getGetStartedPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }


}
