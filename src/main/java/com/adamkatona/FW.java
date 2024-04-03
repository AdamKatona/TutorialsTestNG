package com.adamkatona;

import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FW {
    String home = "https://www.lambdatest.com/selenium-playground/";
    private static WebDriver driver;
    private static boolean isLocalRun;

    public static void setDriver(WebDriver driver) {
        if (FW.driver != null) {
            throw new Error("Webdriver can be instantiated only once!");
        }
        FW.driver = driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public DevTools getDevToolSession() {
        DevTools devTools = ((ChromeDriver) getDriver()).getDevTools();
        devTools.createSession();
        return devTools;
    }

    public ChromeDriver getDriverWithDevTools() {
        this.getDevToolSession();
        return ((ChromeDriver) getDriver());
    }

    private String getScreenshotFileName(ITestResult testResult) {
        long ms = testResult.getEndMillis();
        String date = new Date(ms).toGMTString().replaceAll("[^a-zA-Z\\d\\w]:?", "");
        return ms + "_" + testResult.getName() + "_" + date + ".png";
    }

    private String getScreenshotPath(ITestResult testResult) {
        String path = System.getProperty("user.dir")
                + "/resources/screenshots/"
                + this.getScreenshotFileName(testResult);
        System.out.println(path);
        return path;
    }

    @AfterMethod
    public void takeSnapshot(ITestResult testResult) {
        if (testResult.getStatus() == testResult.FAILURE) {
            File file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            File DestFile = new File(this.getScreenshotPath(testResult));
            try {
                FileUtils.copyFile(file, DestFile);
                // FileHandler.copy(file, DestFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new Error(e);
            }
        }
    }

    public void acceptCookies() {
        By cookieNoticeAcceptAllButton = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until((Function<WebDriver, WebElement>) driver -> {
            WebElement elem = driver.findElement(cookieNoticeAcceptAllButton);
            if (elem.isDisplayed()) {
                return elem;
            }
            return null;
        });
        element.click();
    }

    public URL getLambdaTestUrl() throws MalformedURLException {
        // Set up local credentials, by getting them on your lambdatest account,
        // and create the following env vars below with their values:
        String username = System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";
        return new URL("https://" + username + ":" + accessKey + hub);
    }

    private ChromiumOptions getBrowserOptions(String browser, String version, String platform) {
        ChromiumOptions browserOptions;
        if (browser.equalsIgnoreCase("chrome")) {
            browserOptions = new ChromeOptions();
        } else {
            browserOptions = new EdgeOptions();
        }
        Map<String, Object> ltOptions = new HashMap<>();

        browserOptions.setBrowserVersion(version);
        browserOptions.setPlatformName(platform);

        ltOptions.put("browserName", browser);
//        ltOptions.put("geoLocation", "IN");
        ltOptions.put("build", "TestNG With Java");
        ltOptions.put("name", "Cross Browser Testing");
        ltOptions.put("project", "TutorialsTestNG");
        ltOptions.put("plugin", "java-testNG");
        ltOptions.put("smartUI.project", "SmartProject");
        ltOptions.put("w3c", true);
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);
        browserOptions.setCapability("LT:Options", ltOptions);
        browserOptions.setCapability("cloud:options", ltOptions);
        System.out.println("browserOptions: " + browserOptions);
        return browserOptions;
    }

    /**
     * Whether execution is local or on lambdatest selenium server
     *
     * @param browser  name of the browser config coming from testng config xml
     * @param version  version of the browser config coming from testng config xml
     * @param platform platform of the browser config coming from testng config xml
     * @return a new WebDriver|RemoteWebDriver instance
     * @throws MalformedURLException for the RemoteWebDriver instantiation
     */
    public WebDriver decideForExecutionType(
            String browser,
            String version,
            String platform
    ) throws MalformedURLException {
        if (browser != null) {
            System.out.println("Running tests in selenium grid for lambdatest!");
            isLocalRun = false;
            System.out.println("browser: " + browser);
            System.out.println("version: " + version);
            System.out.println("platform: " + platform);

            return new RemoteWebDriver(this.getLambdaTestUrl(), this.getBrowserOptions(browser, version, platform));
        } else {
            System.out.println("Running tests locally!");
            isLocalRun = true;
            // To fix stuck chromedriver version issue, when selenium+webdrivermanager package versions are incompatible in pom
            // WebDriverManager.chromedriver().clearDriverCache().setup();
            // WebDriverManager.chromedriver().clearResolutionCache().setup();
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
    }

    @Parameters(value = {"URL", "browser", "version", "platform"})
    @BeforeSuite
    public void setUp(
            @Optional String url,
            @Optional String browser,
            @Optional String version,
            @Optional String platform
    ) {
        try {
            setDriver(this.decideForExecutionType(browser, version, platform));
            getDriver().manage().window().maximize();
            // getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(10));
            getDriver().get(url == null ? home : url);
            // Causes timeouts on seleniumgrid
            if (isLocalRun) {
                this.acceptCookies();
            }
        } catch (SessionNotCreatedException | MalformedURLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDown() throws InterruptedException {
//        Thread.sleep(5000);
        getDriver().quit();
    }

    @Parameters({"URL"})
    @BeforeMethod
    public int startWithHome(@Optional String url) {
        String currUrl = getDriver().getCurrentUrl();
        if (url != null && !currUrl.equals(url)) {
            getDriver().get(url);
            System.out.println("USING PARAMETRIZED URL FOR FW BASE_URL: " + url);
            return 0;
        } else if (!currUrl.equals(home)) {
            getDriver().get(home);
            System.out.println("USING CLASS VARIABLE URL FOR FW BASE_URL: " + home);
            return 1;
        }
        return 2;
    }

    protected WebElement find(By locator) {
        return getDriver().findElement(locator);
    }

    protected void click(By locator) {
        this.find(locator).click();
    }

    protected String getText(By locator) {
        String text = this.find(locator).getText();
        System.out.println("Text: " + text);
        return text;
    }
}
