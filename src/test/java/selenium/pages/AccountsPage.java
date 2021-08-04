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

    @FindBy(xpath = "//div//i[@class='absolute bottom-0 right-0 icon-check flex items-center justify-center']")
    private WebElement loggedInIcon;

//    @FindBy(xpath = '//div//i[@class=\'absolute bottom-0 right-0 icon-check flex items-center justify-center\']/../../div[@class=\'text-sm md:text-base text-black-400\']')
//    private WebElement loggedInAccountUserName;

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
