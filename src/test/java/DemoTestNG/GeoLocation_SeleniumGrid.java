package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class GeoLocation_SeleniumGrid extends FW {
    GeoLocation_SeleniumGrid() {
        super();
    }

    @Test
    public void test_geoLocation() {
        getDriver().get("https://where-am-i.org/");
        String location = this.getText(By.id("address"));
        System.out.println(location);
    }
}
