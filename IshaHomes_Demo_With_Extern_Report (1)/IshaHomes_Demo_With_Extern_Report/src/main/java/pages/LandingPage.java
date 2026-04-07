package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class LandingPage
{
    private WebDriver driver;
    private WebDriverWait wait;

    public LandingPage(WebDriver driver)
    {
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public void handleLiveChat() {
        try {
            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//b[normalize-space()='-']")
                    )
            );
            closeBtn.click();
            System.out.println("Live chat closed ");
        } catch (Exception e) {
            System.out.println("Live chat not closed.Skipping ");
        }
    }
}