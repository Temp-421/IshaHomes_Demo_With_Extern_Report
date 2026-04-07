package tests;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CompletedProjectPage;
import pages.ContactUsPage;
import pages.LandingPage;
import utils.BrowserImpl;
import utils.ObjectReader;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class IshaHomeTest {
    WebDriver driver;
    BrowserImpl browser = new BrowserImpl();
    LandingPage landingPage;
    CompletedProjectPage completeproject;
    ContactUsPage contact;

    // Extent Report variables
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUp() throws InterruptedException {
        driver = browser.iniBrowser(ObjectReader.get("Url"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        landingPage = new LandingPage(driver);
        completeproject = new CompletedProjectPage(driver);
        contact = new ContactUsPage(driver);

        // Initialize Extent Reports
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
        extent.attachReporter(spark);
    }

    // Helper method to capture screenshots
    public String captureScreenshot(String name) {
        String path = System.getProperty("user.dir") + "/reports/screenshots/" + name + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "screenshots/" + name + ".png"; // Return relative path for the report
    }

    @Test
    public void verifyCompletedProject() throws InterruptedException {
        test = extent.createTest("Landing Page Verification");
        landingPage.handleLiveChat();
        Thread.sleep(3000);

        String screenshotPath = captureScreenshot("LandingPage");
        test.pass("Landing Page Loaded", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    @Test(dependsOnMethods = "verifyCompletedProject")
    public void ClickOnCompleterProject() {
        test = extent.createTest("Navigate to Completed Projects");
        try {
            String expectedUrl = "https://ishahomes.com/completed-projects/";
            String completedProjectUrl = completeproject.clickCompltedProjects();
            Assert.assertEquals(expectedUrl, completedProjectUrl);
            Thread.sleep(2000);

            String screenshotPath = captureScreenshot("CompletedProjectsPage");
            test.pass("Completed Projects Page Loaded", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (InterruptedException e) {
            test.fail("Navigation failed: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "ClickOnCompleterProject")
    public void countCompletedProjects() {
        test = extent.createTest("Count Projects");
        int expCount = 27;
        int actualCount = completeproject.getCountOfCompletedProjects();
        Assert.assertEquals(expCount, actualCount);
        test.pass("Count verified: " + actualCount);
    }

    @Test(dependsOnMethods = "countCompletedProjects")
    public void ScrollDown() {
        test = extent.createTest("Scroll Down");
        completeproject.scrollDown();
        test.pass("Scrolled down successfully");
    }

    @Test(dependsOnMethods = "ScrollDown")
    public void ClickOnContactUs() {
        test = extent.createTest("Contact Us Page");
        String expUrl = "https://ishahomes.com/contact-us/";
        String actualUrl = contact.ClickOnContact().toString().trim();
        Assert.assertEquals(expUrl, actualUrl);

        String screenshotPath = captureScreenshot("ContactUsPage");
        test.pass("Contact Us Page Loaded", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    @Test(dependsOnMethods = "ClickOnContactUs")
    public void verifyEmailDetails() {
        test = extent.createTest("Verify Email");
        String email = contact.captureAndGetEmail();
        Assert.assertEquals(email, "marketing@ishahomes.com", "Email text mismatch!");
        test.pass("Email verified: " + email);
    }
    @AfterClass
    public void tearDown() {
        extent.flush(); // Essential to save the report
        browser.closeBrowser();
    }
}