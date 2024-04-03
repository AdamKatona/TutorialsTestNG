package DemoTestNG;

import com.adamkatona.FW;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DynamicWaits extends FW {
    DynamicWaits() {
        super();
    }

    @Test
    public void testExplicitWait() {
        getDriver().findElement(By.linkText("Dynamic Data Loading")).click();
        getDriver().findElement(By.id("save")).click();
        By image = By.cssSelector("#loading img[src]");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(image));
        Assert.assertTrue(getDriver().findElement(image).isDisplayed(), "Image is not displayed!");
    }

    @Test
    public void testFluentWait() {
        getDriver().findElement(By.linkText("JQuery Download Progress bars")).click();
        getDriver().findElement(By.id("downloadButton")).click();
        By progressBarDone = By.cssSelector("#dialog #progressbar .ui-progressbar-complete");
        By progressBar = By.xpath("//div[@id='dialog']//div[@class='progress-label']");
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(120))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);

        /**
         * The below function is equivalent with the lambda below
         */
        /*
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement progress = driver.findElement(progressBar);
                String progressBarText = progress.getText();
                System.out.println(progressBarText);
                return progressBarText.equals("Complete!")
                        ? progress
                        : null;
            }
        });
        */

        wait.until((Function<WebDriver, WebElement>) driver -> {
            WebElement progress = driver.findElement(progressBar);
            String progressBarText = progress.getText();
            System.out.println(progressBarText);
            return progressBarText.equals("Complete!")
                    ? progress
                    : null;
        });
        Assert.assertTrue(getDriver().findElement(progressBarDone).isDisplayed(), "Progress bar is not full!");
    }

    @Test
    public void testImplicitWait() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        getDriver().get("https://the-internet.herokuapp.com/dynamic_loading");
        getDriver().findElement(By.partialLinkText("Example 2")).click();
        getDriver().findElement(By.cssSelector("#start button")).click();
        String actMessage = getDriver().findElement(By.cssSelector("#finish h4")).getText();
        Assert.assertEquals(actMessage, "Hello World!");
    }
}
