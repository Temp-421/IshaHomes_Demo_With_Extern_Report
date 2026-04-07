package pages;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
public class ContactUsPage
{
    private WebDriver driver;
    private WebDriverWait wait;
    public ContactUsPage(WebDriver driver)
    {
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public String ClickOnContact() {
        // 1. Define the locator
        By contactUsLocator = By.xpath("//span[@class='elementor-icon-list-text' and text()='Contact Us']");
        // 2. Wait for the element to be present
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement contactBtn = localWait.until(ExpectedConditions.presenceOfElementLocated(contactUsLocator));
        // 3. Scroll to the element so it's centered in the screen
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", contactBtn);
        String contactUrl="";
        // 4. Click it
        try
        {
            contactBtn.click();
            contactUrl=driver.getCurrentUrl();
            System.out.println("Contact Us url"+contactUrl);

        } catch (Exception e) {
            // Backup: Use JS click if the footer is overlapping with a 'Chat' bubble
            js.executeScript("arguments[0].click();", contactBtn);
        }
        System.out.println("Successfully clicked on Contact Us.");
        return contactUrl;
    }
    public String captureAndGetEmail()
    {
        String emailText="";
        try
        {
            WebElement email= driver.findElement(By.linkText("marketing@ishahomes.com"));
            TakesScreenshot ts=(TakesScreenshot)email;
            File SourceFile=ts.getScreenshotAs(OutputType.FILE);
            File destinationFile=new File("C:\\Users\\2478588\\OneDrive - Cognizant\\Desktop\\Interim_Project\\IshaHomes_Demo\\src\\test\\Screenshort\\email.png");

            FileUtils.copyFile(SourceFile,destinationFile);
            emailText=email.getText();
        }
        catch(Exception e){}
        return emailText;
    }
}
