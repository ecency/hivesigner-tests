package selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
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

    @FindBy(xpath = "//a[normalize-space()=\'Add another account\']")
    private WebElement addAnotherAccBtn;

    public void isPageLoaded(){
        accountsList.isDisplayed();
    }

    public String getAccountsPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public void addAccountClick(){
        addAnotherAccBtn.click();
    }

    public void chooseAccount(String username){
        WebElement account = driver.findElement(By.xpath(String.format("//span[normalize-space()='%s']", username)));
        account.click();
    }

    public void isAccoutChoosen(String username){
        WebElement confirmIcon = driver.findElement(By.xpath(String.format("//div[@class='modal-body h-full p-6']//span[normalize-space()='%s']//..//*[local-name()='svg']",username)));
        Assertions.assertEquals(true, confirmIcon.isDisplayed());

    }
    public void returnToGetStartedPage(){
        logoImg.click();
    }

}
