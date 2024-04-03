package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CrossBrowserTesting extends FW {
    CrossBrowserTesting(){
        super();
    }

    @Test
    public void test_SelectDropdownList() {
        this.click(By.linkText("Select Dropdown List"));
        WebElement dropdown = this.find(By.id("select-demo"));
        Select dayDropdown = new Select(dropdown);
        dayDropdown.selectByVisibleText("Saturday");
        Assert.assertEquals(dayDropdown.getFirstSelectedOption().getText(), "Saturday");
    }

    @Test
    public void test_DragAndDropDemo() {
        this.click(By.linkText("Drag and Drop"));
        WebElement source = this.find(By.id("draggable"));
        WebElement target = this.find(By.id("droppable"));
        Actions actions = new Actions(getDriver());
        actions.dragAndDrop(source, target).perform();
    }
}
