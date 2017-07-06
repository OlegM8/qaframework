package com.example.tests;


import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;


import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;


public class TestBase {
    public WebDriver driver;
    public String baseUrl;
    public boolean acceptNextAlert = true;
    public StringBuffer verificationErrors = new StringBuffer();

    ITestResult result;

    @BeforeTest
    public void setUp() throws Exception {

        System.setProperty("webdriver.gecko.driver", "/Users/DISTILLERY/IdeaProjects/geckodriver");

        driver = new FirefoxDriver();
        baseUrl = "https://distillery.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            result.setStatus(ITestResult.FAILURE);
            Reporter.setCurrentTestResult(result);
        }
    }

    public void goToStart() throws InterruptedException {

        openMainPage();
        useDelay();
        goToMenu();
        goTo(new GoToClass("main-nav--work")); //our work
        useDelay();
        goTo(new GoToClass("link-overlay-cizo")); //cizo
        useDelay();
    }

    protected void useTestCase(UseTestCaseClass useTestCaseClass) {
        testCaseDoing(useTestCaseClass);
    }

    private void testCaseDoing(UseTestCaseClass useTestCaseClass) {
        try {
            assertEquals(useTestCaseClass.getLabelTextParagraph(), driver.findElement(By.cssSelector(useTestCaseClass.getLabelTestSelector())).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    protected void goTo(GoToClass goToClass) {
        driver.findElement(By.id(goToClass.getElementId())).click();
    }

    protected void useDelay() throws InterruptedException {
        // delay 5 sec
        Thread.sleep(5000);
    }

    protected void goToMenu() {
        // go to Menu
        driver.findElement(By.cssSelector("span.icon-menu__state")).click();
    }

    public void openMainPage() {
        // open main page
        driver.get(baseUrl + "/");
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
