package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TakeFailedScreenshots extends FW {
    TakeFailedScreenshots() {
        super();
    }

    @Test
    public void testSimpleFormDemo() {
        getDriver().findElement(By.linkText("Simple Form Demo")).click();
        getDriver().findElement(By.xpath("//p[text()='Enter Message']//following-sibling::input"))
                .sendKeys("Lambdatest is awesome!!!");
        getDriver().findElement(By.id("showInput")).click();
        String message = getDriver().findElement(By.id("message")).getText();
        Assert.assertEquals(message, "Potatoooo!!!");
    }
}
