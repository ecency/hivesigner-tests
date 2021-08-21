package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationBottomLine {
    private WebDriver driver;

    public NavigationBottomLine(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static NavigationBottomLine using(WebDriver driver) {
        return new NavigationBottomLine(driver);
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


    public NavigationBottomLine appsClick(){
        this.appsBottomLink.click();
        return this;
    }

    public NavigationBottomLine accountsClick(){
        this.accountsBottomLink.click();
        return this;
    }

    public NavigationBottomLine signerClick(){
        this.signerBottomLink.click();
        return this;
    }

    public NavigationBottomLine docsClick(){
        this.docsBottomLink.click();
        return this;
    }

    public NavigationBottomLine aboutClick(){
        this.aboutBottomLink.click();
        return this;
    }
}