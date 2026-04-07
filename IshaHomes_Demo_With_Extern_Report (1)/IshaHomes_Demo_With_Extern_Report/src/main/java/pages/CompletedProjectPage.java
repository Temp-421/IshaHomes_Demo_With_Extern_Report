package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CompletedProjectPage
{
    private WebDriver driver;
    private WebDriverWait wait;
    public CompletedProjectPage(WebDriver driver)
    {
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public String clickCompltedProjects() {
        // Locate the element using the ID you provided in your code
        By locator = By.xpath("//li[@id='menu-item-25810']//a[contains(@class,'nav-link')]");
        String currentUrl="";
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(locator));

        try {
            // Method A: Normal Click
            menu.click();
            currentUrl=driver.getCurrentUrl();
        } catch (Exception e) {
            // Method B: JavaScript Click (Forces the click even if something is in the way)
            System.out.println("Normal click failed or blocked, trying JavaScript click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", menu);
        }
        System.out.println("Navigation command executed.");
        return  currentUrl;
    }
    public int getCountOfCompletedProjects(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.urlContains("completed-projects"));

        By cardLocator = By.xpath("//div[@id='module_properties']/div[contains(@class,'item-listing-wrap')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(cardLocator));

        List<WebElement> allCards = driver.findElements(cardLocator);
        List<WebElement> actualCards = allCards.stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());
        System.out.println("Total Number of Completed Projects : " + actualCards.size());
        System.out.println("Fist Five Projects Completed By IshaHomes : ");
        List<WebElement> firstFiveCards = allCards.stream()
                .filter(WebElement::isDisplayed)
                .limit(5)
                .collect(Collectors.toList());

        for (int i = 0; i < firstFiveCards.size(); i++) {
            String fullText = firstFiveCards.get(i).getText();

            String[] lines = fullText.split("\\n");
            String projectName = lines[lines.length - 1].trim();
            // 5. Output the clean version
            System.out.println("#" + (i + 1) + " Project Name : " + projectName);
        }
        return actualCards.size();
    }
    public void scrollDown() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Scrolls to the absolute bottom of the page
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            System.out.println("Scrolled to the end of the page successfully.");

            // Optional: Small sleep just to visually confirm the scroll in the browser
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Could not scroll to bottom: " + e.getMessage());
        }
    }

}