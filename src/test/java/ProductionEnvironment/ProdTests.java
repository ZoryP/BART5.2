package ProductionEnvironment;
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
import java.time.LocalDateTime;
import java.util.List;


public class ProdTests {
    WebDriver driver;
    String url = "https://bart.skf.com/";

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
    public void CloseBrowser(ITestResult result) {
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
    public void PageTitle() throws Throwable{
        Assert.assertEquals(driver.getTitle(), "BART 5.2");
        Thread.sleep(2000);
        Assert.assertEquals(driver.getCurrentUrl(),"https://bart.skf.com/");
    }

    @Test(priority = 2)
    public void Login() throws Throwable {
        BartPageProd bartPageProd = new BartPageProd(driver);
        bartPageProd.login("testuser1.bart@gmail.com", "bartTest");
        Thread.sleep(2000);
        assert bartPageProd.ErrorLoginMassage.isDisplayed();
        assert bartPageProd.ForgotPassword.isEnabled();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        bartPageProd.login("testuser1.bart@gmail.com", "bartTest1");
        Thread.sleep(2000);
        assert bartPageProd.WelcomeMassage.isDisplayed();
    }

    @Test(priority = 3)
    public void Header() throws Throwable {
        BartPageProd bartPageProd = new BartPageProd(driver);
        Thread.sleep(2000);
        assert bartPageProd.CreateNewReport.isDisplayed();
        assert bartPageProd.MyReports.isDisplayed();
        assert bartPageProd.AllReports.isDisplayed();
        assert bartPageProd.Analytics.isDisplayed();
        assert bartPageProd.FindNAM.isDisplayed();
        assert bartPageProd.Share.isDisplayed();
        assert bartPageProd.Settings.isDisplayed();
        assert bartPageProd.SignOut.isDisplayed();
        System.out.printf("Create New Report button %nMy reports button %nAll report button %nAnalytics button %nFind NAM Inspection report button %nShare button %nSettings button %nSign out button %nAre displayed on the page.");
    }

        @Test (priority = 4)
        public void assertMenuElements() {
            BartPageProd bartPageProd = new BartPageProd(driver);
            bartPageProd.CreateNewReport.click();
            List<WebElement> ReportsMenu = driver.findElements(By.cssSelector(".navigation__sub-link"));
            Assert.assertEquals(ReportsMenu.size(), 5);
    }
}























