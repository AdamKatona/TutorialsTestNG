package DemoTestNG;

import com.adamkatona.FW;
import org.testng.annotations.Test;

public class ParallelTesting1 extends FW {
    ParallelTesting1() {
        super();
    }

    @Test
    public void test1_jQueryDatePicker() {
        getDriver().get("https://www.lambdatest.com/selenium-playground/jquery-date-picker-demo");
        System.out.println(Thread.currentThread().getId() + ": Jquery Date Picker Page");
    }

    @Test
    public void test2_DataListFilter() {
        getDriver().get("https://www.lambdatest.com/selenium-playground/data-list-filter-demo");
        System.out.println(Thread.currentThread().getId() + ": Data List Filter Page");
    }
}
