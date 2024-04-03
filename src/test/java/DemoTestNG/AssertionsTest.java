package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssertionsTest extends FW {
    AssertionsTest() {
        super();
    }

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testSingleCheckbox() {
        getDriver().findElement(By.linkText("Checkbox Demo")).click();
        getDriver().findElement(By.id("isAgeSelected")).click();
        WebElement element = getDriver().findElement(By.id("txtAge"));
        String actualMessage = element.getText();
        Assert.assertEquals(actualMessage, "Checked");
        Assert.assertTrue(actualMessage.contains("Checked"));
    }

    @Test
    public void testRadioButtons() throws InterruptedException {
        getDriver().findElement(By.linkText("Radio Buttons Demo")).click();
        getDriver().findElement(By.cssSelector("input[value=\"Other\"]")).click();
        getDriver().findElement(By.cssSelector("input[value*=\"5 - 15\"]")).click();
        getDriver().findElement(By.xpath("//button[text()=\"Get values\"]")).click();
        String actualGender = getDriver().findElement(By.cssSelector(".genderbutton")).getText();
        String actualAgeGroup = getDriver().findElement(By.cssSelector(".groupradiobutton")).getText();
        softAssert.assertEquals(actualGender, "Other");
        softAssert.assertTrue(actualAgeGroup.contains("15"));
        softAssert.assertAll("\n Test Soft Assert");
    }
}
