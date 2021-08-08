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

    @FindBy(xpath = "//div//div[@class='flex justify-center mt-6 md:mt-20 xl:pt-10']/a")
    private WebElement addAnotherAccBtn;

    @FindBy(xpath = "//div[@class='modal flex items-center justify-center top-0 left-0 bottom-0 right-0 fixed mobile-full']//input[@id=\"importKey\"]")
    private WebElement confirmLocalPassword;

    @FindBy(xpath = "//div[@class='modal flex items-center justify-center top-0 left-0 bottom-0 right-0 fixed mobile-full']")
    private WebElement confirmPasswordPanel;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement getConfirmLocalPasswordLogin;

    @FindBy(xpath = "//a[normalize-space()='Authorities']")
    private WebElement authorities;

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

    public void isAccountChoosen(String username){
        WebElement confirmIcon = driver.findElement(By.xpath(String.format("//div[@class='modal-body h-full p-6']//span[normalize-space()='%s']//..//*[local-name()='svg']",username)));
        Assertions.assertEquals(true, confirmIcon.isDisplayed());

    }
    public void returnToGetStartedPage(){
        logoImg.click();
    }

    public void inputConfirmLocalPassword(String password){
            Assertions.assertEquals(true, confirmPasswordPanel.isDisplayed());
            confirmLocalPassword.sendKeys(password);
            getConfirmLocalPasswordLogin.click();
    }

    public void authoritiesClick(String username){
        WebElement confirmIcon = driver.findElement(By.xpath(String.format("//div[@class='account-item flex flex-col items-center cursor-pointer']//span[normalize-space()='%s']/..",username)));
        confirmIcon.click();
        authorities.click();
    }
}
