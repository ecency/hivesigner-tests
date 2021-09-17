package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.ConfProperties;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static LoginPage using(WebDriver driver) {
        return new LoginPage(driver);
    }

    public LoginPage navigateToPage() {
        driver.get(ConfProperties.getProperty("LOGIN_PAGE"));
        return this;
    }

    @FindBy(css = "[data-e2e=\"import-user-form-submit-next\"]")
    private WebElement continueBtn;

    @FindBy(css = "[data-e2e=\"add-another-account\"]+div>a")
    private WebElement dontHaveAccount;

    @FindBy(css = "[data-e2e=\"login-switch-account\"]")
    private WebElement accountDropdown;

    @FindBy(css ="[data-e2e=\"login-password\"]>div[class=\"relative\"]>input")
    private WebElement hivesignerPasswordInput;

    @FindBy(css = "[data-e2e=\"add-another-account\"]")
    private WebElement addAnotherAccBtn;

    public LoginPage clickContinueButton() {
        this.continueBtn.click();
        System.out.println("Click on 'Continue' button...");
        return this;
    }

    public LoginPage dropdownSelect() {
        this.accountDropdown.click();
        System.out.println("Click on dropdown list...");
        return this;
    }

    public LoginPage addAccountClick() {
        this.addAnotherAccBtn.click();
        System.out.println("Click on 'Add another account' button...");
        return this;
    }

    public LoginPage chooseAccountFromSelect(String username) {
        this.accountDropdown.click();
        System.out.println("Choose account from dropdown list...");
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class='select-option cursor-pointer py-4 px-5 hover:bg-primary-100 bg-gray-200']//div[@class='flex items-center justify-start'][normalize-space()='%s']", username)));
        accountSelect.click();
        clickContinueButton();
        return this;
    }

    public LoginPage checkDropdownWithAccount(String username) {
        System.out.println("Check that account is in dropdown list...");
        this.accountDropdown.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class='select-options border border-black-500 rounded-md mt-4 absolute z-10 w-full bg-white overflow-x-hidden overflow-y-auto']//div[@class='flex items-center justify-start'][normalize-space()='%s']", username)));
        accountSelect.isDisplayed();
        System.out.println("Checked...");
        accountSelect.click();
        return this;
    }

    public LoginPage isPageLoaded() {
        this.dontHaveAccount.isDisplayed();
        System.out.println("Login page is loaded...");
        return this;
    }

    public String getPageUrl() {
        System.out.println("Check that current page url is correct...");
        return driver.getCurrentUrl();
    }

    public LoginPage inputHivesignerPassword(String password) {
        System.out.println("Input password...");
        this.hivesignerPasswordInput.sendKeys(password);
        return this;
    }

    public boolean isPasswordFieldPresent() {
        try {
            hivesignerPasswordInput.isDisplayed();
            System.out.println("Password input field is displayed...");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Password input field is not displayed...");
            return false;
        }
    }
}