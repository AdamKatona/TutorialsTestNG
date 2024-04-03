package DemoTestNG;

import com.adamkatona.FW;
import org.testng.annotations.Test;

public class ParallelTesting2 extends FW {
    ParallelTesting2() {
        super();
    }

    @Test(threadPoolSize = 3, invocationCount = 4)
    public void test3_BootstrapAlerts() {
        getDriver().get("https://www.lambdatest.com/selenium-playground/bootstrap-alert-messages-demo");
        System.out.println(Thread.currentThread().getId() + ": Bootstrap Alert Message Page");
    }

    @Test
    public void test4_DragAndDrop() {
        getDriver().get("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");
        System.out.println(Thread.currentThread().getId() + ": Drag and Drop demo Page");
    }
}
