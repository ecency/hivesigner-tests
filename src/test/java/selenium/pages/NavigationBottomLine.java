package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationBottomLine {
    public WebDriver driver;

    public NavigationBottomLine(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@class='flex w-full pt-28 hidden sm:flex items-center justify-between']//a[normalize-space()='Apps']")
    private WebElement appsBottomLink;

    @FindBy(xpath = "//div[@class='flex w-full pt-28 hidden sm:flex items-center justify-between']//a[normalize-space()='Accounts']")
    private WebElement accountsBottomLink;

    @FindBy(xpath = "//div[@class='flex w-full pt-28 hidden sm:flex items-center justify-between']//a[normalize-space()='Signer']")
    private WebElement signerBottomLink;

    @FindBy(xpath = "//div[@class='flex w-full pt-28 hidden sm:flex items-center justify-between']//a[normalize-space()='Docs']")
    private WebElement docsBottomLink;

    @FindBy(xpath = "//div[@class='flex w-full pt-28 hidden sm:flex items-center justify-between']//a[normalize-space()='About']")
    private WebElement aboutBottomLink;


    public void appsClick(){
        appsBottomLink.click();
    }

    public void accountsClick(){
        accountsBottomLink.click();
    }

    public void signerClick(){
        signerBottomLink.click();
    }

    public void docsClick(){
        docsBottomLink.click();
    }

    public void aboutClick(){
        aboutBottomLink.click();
    }
}