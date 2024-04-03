package DemoTestNG;

import org.testng.annotations.*;

@Test(groups = "smoke")
public class ConfigurationAnnotations_2_ListBox {
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("    (4) Execute before each test method");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("    (4) Execute after each test method\n");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("   (3) Execute before class: List Box");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("   (3) Execute after class: List Box");
    }

    public void test3_BootstrapListBox() {
        System.out.println("     (5) Test Method 3: Bootstrap List ");
    }

    public void test4_JQueryListBox() {
        System.out.println("     (5) Test Method 4: JQuery List Box");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("  (2) Execute before each test");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("  (2) Execute after each test");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println(" (1) Execute before each suite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println(" (1) Execute after each suite");
    }

    @BeforeGroups(groups = {"regression", "smoke"})
    public void beforeGroups() {
        System.out.println("Execute before group:");
    }

    @AfterGroups(groups = {"regression", "smoke"})
    public void AfterGroups() {
        System.out.println("Execute after group:");
    }
}




















