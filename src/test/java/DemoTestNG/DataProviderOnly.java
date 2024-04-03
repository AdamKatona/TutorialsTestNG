package DemoTestNG;

import org.testng.annotations.DataProvider;

public class DataProviderOnly {

    @DataProvider(name = "input-provider")
    public static Object[][] inputData() {
        Object[][] data = new Object[2][3];
        data[0][0] = "Joe";     data[0][1] = "Joe@gmail.com"; data[0][2] = 25;
        data[1][0] = "Jane";     data[1][1] = "Jane@gmail.com"; data[1][2] = 20;

        return data;
    }
}
