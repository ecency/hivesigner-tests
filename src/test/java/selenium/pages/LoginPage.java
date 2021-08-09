package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//button[normalize-space()='Continue']")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[contains(text(), 'Don`t have an account?')]")
    private WebElement dontHaveAccount;

    @FindBy(xpath = "//div/div[@class='mb-2']//div[@class='select text-lg relative text-black-500 z-20']")
    private WebElement accountDropdown;


    public void clickContinueButton() {
        continueBtn.click();
    }

    public void dropdownSelect() {
        accountDropdown.click();
    }

    public void setAccount(String account){
        accountDropdown.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class='select-option cursor-pointer py-4 px-5 hover:bg-primary-100 bg-gray-200']//div[@class='flex items-center justify-start'][normalize-space()='%s']", account)));
        accountSelect.click();
        clickContinueButton();
    }

    public void checkDropdownWithAccount(String account){
        accountDropdown.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class='select-option cursor-pointer py-4 px-5 hover:bg-primary-100 bg-gray-200']//div[@class='flex items-center justify-start'][normalize-space()='%s']", account)));
        accountSelect.isDisplayed();
    }

    public void isPageLoaded() {
        dontHaveAccount.isDisplayed();
    }

    public String getPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }
}
