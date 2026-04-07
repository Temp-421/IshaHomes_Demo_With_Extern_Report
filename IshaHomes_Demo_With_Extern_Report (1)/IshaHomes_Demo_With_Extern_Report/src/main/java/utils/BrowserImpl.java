package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class BrowserImpl {

    private WebDriver driver;

    public WebDriver iniBrowser(String url)
    {
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);

        System.out.println("Browser launched: " + url);
        return driver;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser Closed");
        }
    }
}
