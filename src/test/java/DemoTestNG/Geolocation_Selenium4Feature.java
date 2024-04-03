package DemoTestNG;

import com.adamkatona.FW;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.v122.emulation.Emulation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Optional;

public class Geolocation_Selenium4Feature extends FW {
    Geolocation_Selenium4Feature() {
        super();
    }

    @Test
    public void mockGeoLocation_executeCDPCommand() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("latitude", 25.1150);
        coordinates.put("longitude", 55.1626);
        coordinates.put("accuracy", 1.0);
        getDriverWithDevTools().executeCdpCommand("Emulation.setGeolocationOverride", (HashMap) coordinates);
        getDriverWithDevTools().get("https://where-am-i.org/");
        String location = this.getText(By.id("address"));
        System.out.println(location);
        Assert.assertTrue(location.contains("Dubai, United Arab Emirates"));
    }

    @Test
    public void mockGeoLocation_executeCDPCommand2() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("latitude", 25.1150);
        coordinates.put("longitude", 55.1626);
        coordinates.put("accuracy", 1.0);
        getDevToolSession().send(
                Emulation.setGeolocationOverride(
                        Optional.of(25.1150),
                        Optional.of(55.1626),
                        Optional.of(1)
                )
        );
        getDriverWithDevTools().get("https://where-am-i.org/");
        String location = this.getText(By.id("address"));
        System.out.println(location);
        Assert.assertTrue(location.contains("Dubai, United Arab Emirates"));
    }
}
