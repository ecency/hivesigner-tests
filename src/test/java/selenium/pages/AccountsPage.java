package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountsPage {
    public WebDriver driver;
    public AccountsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = ".accounts-list")
    private WebElement accountsList;

    @FindBy(xpath = "//div//span[text()='Accounts']")
    private WebElement logoImg;

    public void isPageLoaded(){
        accountsList.isDisplayed();
    }

    public String getAccountsPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public void returnToGetStartedPage(){
        logoImg.click();
    }

}
