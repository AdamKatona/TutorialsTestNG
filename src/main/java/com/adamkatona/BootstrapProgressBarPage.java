package com.adamkatona;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BootstrapProgressBarPage extends FW {
    BootstrapProgressBarPage() {
        super();
    }

    private final By startDownloadButton = By.id("dwnBtn");
    private final By progressBarPercentage = By.cssSelector(".counter");
    private final By successText = By.cssSelector("p.success");

    public void clickStartDownloadButton() {
        this.click(startDownloadButton);
    }

    public String getProgressBarPercentageText() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(progressBarPercentage));
        return this.getText(progressBarPercentage);
    }

    public boolean isDownloadSuccessful() {
        return this.find(successText).isDisplayed();
    }
}
