package com.adamkatona;

import org.openqa.selenium.By;

public class HomePage extends FW {
    HomePage() {
        super();
    }

    private final By bootstrapProgressBar = By.linkText("Bootstrap Progress bar");

    public BootstrapProgressBarPage clickBootstrapProgressBar() {
        this.click(bootstrapProgressBar);
        return new BootstrapProgressBarPage();
    }
}
