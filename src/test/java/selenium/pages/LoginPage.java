package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    @FindBy(id = "key")
    private WebElement hivesignerPasswordInput;

    @FindBy(xpath = "//div//div[@class=\"mb-2\"]//a[@class=\"button block text-center mb-2\"]")
    private WebElement addAnotherAccBtn;

    public void clickContinueButton() {
        continueBtn.click();
    }

    public void dropdownSelect() {
        accountDropdown.click();
    }

    public void addAccountClick() {
        addAnotherAccBtn.click();
    }

    public void chooseAccountFromSelect(String username) {
        accountDropdown.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class='select-option cursor-pointer py-4 px-5 hover:bg-primary-100 bg-gray-200']//div[@class='flex items-center justify-start'][normalize-space()='%s']", username)));
        accountSelect.click();
        clickContinueButton();
    }

    public void checkDropdownWithAccount(String account) {
        accountDropdown.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class='select-options border border-black-500 rounded-md mt-4 absolute z-10 w-full bg-white overflow-x-hidden overflow-y-auto']//div[@class='flex items-center justify-start'][normalize-space()='%s']", account)));
        accountSelect.isDisplayed();
        accountSelect.click();
    }

    public void isPageLoaded() {
        dontHaveAccount.isDisplayed();
    }

    public String getPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public void inputHivesignerPassword(String password) {
        hivesignerPasswordInput.sendKeys(password);
    }

    public boolean isPasswordFieldPresent() {
        try {
            return hivesignerPasswordInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}