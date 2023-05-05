package DevEnvironment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BartPageDev {
    static WebDriver driver;
    @FindBy(id = "email")
    WebElement email;
    @FindBy(id = "password")
    WebElement password;
    @FindBy(className = "button__text")
    WebElement enterBtn;

    @FindBy(xpath = "//div[@class='login__errors']")
    WebElement ErrorLoginMassage;

    @FindBy(xpath = "//a[normalize-space()='Forgot password?']")
    WebElement ForgotPassword;

    @FindBy(xpath = "//div[@class='welcome-information__user']")
    WebElement WelcomeMassage;

    @FindBy(xpath = "//button[@class='navigation__action navigation__action--toggler']")
    WebElement CreateNewReport;

    @FindBy(xpath = "//a[normalize-space()='My Reports']")
    static
    WebElement MyReports;

    @FindBy(xpath = "//a[normalize-space()='All Reports']")
    static
    WebElement AllReports;

    @FindBy(css = ".application-feedback__toggler")
    WebElement Share;

    @FindBy(xpath = "//button[@class='header-settings__toggler']//*[name()='svg']")
    WebElement Settings;

    @FindBy(xpath = "//button[@class='user-bar__sign-out']")
    WebElement SignOut;

    @FindBy(xpath = "//a[normalize-space()='Analytics']")
    static
    WebElement Analytics;

    @FindBy(xpath = "//a[normalize-space()='Find NAM Inspection Reports']")
    static
    WebElement FindNAM;


    public void login(String email, String password) throws Throwable {
        this.email.click();
        this.email.sendKeys(email);
        this.password.click();
        this.password.sendKeys(password);
        enterBtn.click();
        Thread.sleep(2000);

    }
    BartPageDev(WebDriver driver) {

        BartPageDev.driver = driver;
        PageFactory.initElements(driver, this);
    }

}