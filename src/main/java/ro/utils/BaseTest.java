package ro.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Istvan on 03.05.2015.
 */
public class BaseTest {

    //    for BaseTest
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    //    for any test extending BaseTest
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @BeforeSuite(alwaysRun = true)
    private void suiteSetup() {
        LOGGER.info("suiteSetup");
    }

    @BeforeClass(alwaysRun = true)
    private void classSetup(ITest test) {
        LOGGER.info("classSetup");
        LOGGER.info("Running test class: {}.{}", test.getTestName());
    }

    @BeforeMethod(alwaysRun = true)
    private void methodSetup(ITest test, ITestNGMethod testNGMethod) {
        LOGGER.info("methodSetup");
        LOGGER.info("Running test method: {}", testNGMethod.getMethodName());
    }

    @AfterMethod(alwaysRun = true)
    private void methodTearDown(ITestResult testResult) throws IOException {
        LOGGER.info("methodTearDown");
        String workingDirectory = System.getProperty("user.dir");
        System.out.println(workingDirectory);
        if (testResult.getStatus() == ITestResult.FAILURE) {
            File screenshot = ((TakesScreenshot) BasePage.getDriver()).getScreenshotAs(OutputType.FILE);
            String pageSource = BasePage.getDriver().getPageSource();
            String fileNamePrefix = String.format("%s/%s.%s", workingDirectory, testResult.getTestName(), testResult.getMethod());
            FileUtils.copyFile(screenshot, new File(String.format("%s.jpg", fileNamePrefix)));
            File pageSourceFile = new File(pageSource);
            FileUtils.copyFile(pageSourceFile, new File(String.format("%s.html", fileNamePrefix)));
        }
    }

    @AfterClass(alwaysRun = true)
    public void classTearDown(ITestContext testContext) {
        LOGGER.info("classTearDown");
        LOGGER.info("testContext.getSuite().getXmlSuite().getTests().size(): {}", testContext.getSuite().getXmlSuite().getTests().size());
        if (testContext.getSuite().getXmlSuite().getTests().size() == 1) {
            BasePage.getDriver().quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    private void suiteTearDown(ITestContext testContext) {
        LOGGER.info("suiteTearDown");
        LOGGER.info("testContext.getSuite().getXmlSuite().getTests().size(): {}", testContext.getSuite().getXmlSuite().getTests().size());
        if (testContext.getSuite().getXmlSuite().getTests().size() > 1) {
            BasePage.getDriver().quit();
        }
    }
}