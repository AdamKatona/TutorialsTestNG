package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametrizedTest extends FW {
    ParametrizedTest() {
        super();
    }

    @Test
    @Parameters({"Task", "TestResult"})
    public void testFileDownload(@Optional String task, @Optional String testResult) {
        getDriver().findElement(By.linkText("File Download")).click();
        getDriver().findElement(By.id("textbox")).sendKeys(task + " Execution: " + testResult);
        getDriver().findElement(By.id("create")).click();
        getDriver().findElement(By.id("link-to-download")).click();
    }
}
