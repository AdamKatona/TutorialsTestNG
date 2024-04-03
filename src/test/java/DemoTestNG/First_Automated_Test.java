package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class First_Automated_Test extends FW {
    First_Automated_Test() {
        super();
    }

    @Test(priority = 1)
    public void testTableSortAndSearch() {
        getDriver().findElement(By.linkText("Table Sort & Search")).click();
        getDriver().findElement(By.cssSelector("#example_filter [type=\"search\"]"))
                .sendKeys("A. Satou");
    }

    @Test(priority = 2)
    public void testBootstrapDatePicker() {
        getDriver().findElement(By.linkText("Bootstrap Date Picker")).click();
        getDriver().findElement(By.id("birthday"))
                .sendKeys("06/07/2008");
    }
}
