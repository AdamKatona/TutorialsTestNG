package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class DataProvidersTest extends FW {
    DataProvidersTest(){
        super();
    }

    @DataProvider()
    public Object[][] ajaxData() {
        Object[][] data = new Object[2][2];
        data[0][0] = "Joe Doe";     data[0][1] = "Tester Joe Doe";
        data[1][0] = "Jane Doe";     data[1][1] = "Tester Jane Doe";

        return data;
    }

    @Test(dataProvider = "ajaxData")
    public void test_AjaxForm(String name, String comment){
        System.out.println(Thread.currentThread().getId() + ": test_AjaxForm");
        System.out.println("Name: " + name);
        System.out.println("Comment: " + comment);

        this.click(By.linkText("Ajax Form Submit"));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));

        this.find(By.id("title")).sendKeys(name);
        this.find(By.id("description")).sendKeys(comment);
        this.click(By.id("btn-submit"));
    }

    @Test(dataProviderClass = DataProviderOnly.class, dataProvider = "input-provider")
    public void testInputFields(String name, String email, int age){
        System.out.println("Age: " + age);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        this.click(By.linkText("Input Form Submit"));

        this.find(By.id("name")).sendKeys(name);
        this.find(By.id("inputEmail4")).sendKeys(email);
    }
}
