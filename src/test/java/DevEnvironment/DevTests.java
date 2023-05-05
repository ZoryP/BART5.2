package DevEnvironment;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


public class DevTests {
    WebDriver driver;
    String url = "https://dnnfsk8ppi4ki.cloudfront.net/";

    @BeforeClass
    public void SetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
    }

    @AfterClass
    public void QuitBrowser() {
        driver.quit();
    }

    @AfterMethod
    public void CloseBrowser(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                String dateTime = LocalDateTime.now().toString();
                File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screen, new File("./Screenshots/" + result.getName() +
                        dateTime.replace(":", "_") + ".png"));
            } catch (Exception e) {
                System.out.println("Success");
            }
        }
    }

    @Test(priority = 1)
    public void PageTitle() throws Throwable {
        Assert.assertEquals(driver.getTitle(), "BART 5.2");
        Thread.sleep(2000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://dnnfsk8ppi4ki.cloudfront.net/");
    }

    @Test(priority = 2)
    public void Login() throws Throwable {
        BartPageDev bartPageDev = new BartPageDev(driver);
        bartPageDev.login("testuser1.bart@gmail.com", "bartTest");
        Thread.sleep(2000);
        assert bartPageDev.ErrorLoginMassage.isDisplayed();
        assert bartPageDev.ForgotPassword.isEnabled();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        bartPageDev.login("testuser1.bart@gmail.com", "bartTest1");
        Thread.sleep(2000);
        assert bartPageDev.WelcomeMassage.isDisplayed();
    }

    @Test(priority = 3)
    public void Header() throws Throwable {
        BartPageDev bartPageDev = new BartPageDev(driver);
        Thread.sleep(2000);
        assert bartPageDev.CreateNewReport.isDisplayed();
        assert BartPageDev.MyReports.isDisplayed();
        assert BartPageDev.AllReports.isDisplayed();
        assert BartPageDev.Analytics.isDisplayed();
        assert BartPageDev.FindNAM.isDisplayed();
        assert bartPageDev.Share.isDisplayed();
        assert bartPageDev.Settings.isDisplayed();
        assert bartPageDev.SignOut.isDisplayed();
        System.out.printf("Create New Report button %nMy reports button %nAll report button %nAnalytics button %nFind NAM Inspection report button %nShare button %nSettings button %nSign out button %nAre displayed on the page.");
    }

    @Test(priority = 4)
    public void assertMenuElements() {
        BartPageDev bartPageProd = new BartPageDev(driver);
        bartPageProd.CreateNewReport.click();
        List<WebElement> ReportsMenu = driver.findElements(By.cssSelector(".navigation__sub-link"));
        Assert.assertEquals(ReportsMenu.size(), 5);
    }
}
