package ro.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Istvan on 29.03.2015.
 */
public class EnhancedDriver extends RemoteWebDriver {

    private static final Logger LOG = LoggerFactory.getLogger(EnhancedDriver.class);

    public EnhancedDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    public void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void implicitlyWait(int time, TimeUnit timeUnit) {
        manage().timeouts().implicitlyWait(time, timeUnit);
    }

    public void implicitlyWaitSeconds(int seconds) {
        manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public void implicitlyWaitOneSecond() {
        manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    public void implicitlyWaitDefault() {
        manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void mouseOver(By by){
        WebElement element = findElement(by);
        mouseOver(element);
    }

    public void mouseOver(WebElement element){
        Actions action = new Actions(this);
        action.moveToElement(element).build().perform();
    }

    public WebElement getElement(boolean asserted, By by, int seconds) {
        Timer timer = new Timer(seconds);
        implicitlyWaitOneSecond();
        while (timer.hasMoreTime()) {
            try {
                WebElement element = findElement(by);
                implicitlyWaitDefault();
                return element;
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element matching the '%s' selector was found after %d seconds!", by, seconds));
        return null;
    }

    public WebElement getElement(boolean asserted, WebElement parentElement, By by, int seconds) {
        Timer timer = new Timer(seconds);
        implicitlyWaitOneSecond();
        while (timer.hasMoreTime()) {
            try {
                WebElement element = parentElement.findElement(by);
                implicitlyWaitDefault();
                return element;
            } catch (StaleElementReferenceException e1) {
                e1.printStackTrace();
                LOG.error(e1.getMessage());
//				if we get a StaleElementReferenceException we need to pass a fresh instance of parentElement to the method and call it again
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                LOG.error(e2.getMessage());
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element matching the '%s' selector was found after %d seconds!", by, seconds));
        return null;
    }

    public List<WebElement> getElements(boolean asserted, WebElement parentElement, By by, int seconds) {
        Timer timer = new Timer(seconds);
        implicitlyWaitOneSecond();
        while (timer.hasMoreTime()) {
            try {
                List<WebElement> elements = parentElement.findElements(by);
                implicitlyWaitDefault();
                LOG.debug("elements size: {}", elements.size());
                return elements;
            } catch (StaleElementReferenceException e1) {
                e1.printStackTrace();
                LOG.error(e1.getMessage());
//				if we get a StaleElementReferenceException we need to pass a fresh instance of parentElement to the method and call it again
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                LOG.error(e2.getMessage());
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element matching the '%s' selector was found after %d seconds!", by, seconds));
        return null;
    }

    public List<WebElement> getElements(boolean asserted, By by, int seconds) {
        Timer timer = new Timer(seconds);
        implicitlyWaitOneSecond();
        while (timer.hasMoreTime()) {
            try {
                List<WebElement> elements = findElements(by);
                implicitlyWaitDefault();
                return elements;
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(e.getMessage());
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element matching the '%s' selector was found after %d seconds!", by, seconds));
        return null;
    }

    public WebElement getElementContainingTerms(boolean asserted, WebElement parentElement, By by, boolean ignoreCase, int seconds, String... terms) {
        Timer timer = new Timer(seconds);
        while (timer.hasMoreTime()) {
            List<WebElement> elements = getElements(asserted, parentElement, by, seconds);
            if (elements != null) {
                implicitlyWaitOneSecond();
                for (WebElement element : elements) {
                    String elementText = element.getText();
                    LOG.debug("elementText: {}", elementText);
                    boolean elementMatches = StringUtils.checkIfTextContainsTerms(elementText, ignoreCase, terms);
                    if (elementMatches) {
                        implicitlyWaitDefault();
                        return element;
                    }
                }
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element containing the '%s' terms was found after %d seconds!", StringUtils.getVarargsAsString(terms),
                seconds));
        return null;
    }

    public WebElement getElementContainingTerms(boolean asserted, By by, boolean ignoreCase, int seconds, String... terms) {
        Timer timer = new Timer(seconds);
        while (timer.hasMoreTime()) {
            List<WebElement> elements = getElements(asserted, by, seconds);
            LOG.debug("elements size: {}", elements.size());
            implicitlyWaitOneSecond();
            for (WebElement element : elements) {
                String elementText = element.getText();
                LOG.debug("elementText: {}", elementText);
                boolean elementMatches = StringUtils.checkIfTextContainsTerms(elementText, ignoreCase, terms);
                if (elementMatches) {
                    implicitlyWaitDefault();
                    return element;
                }
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element containing the '%s' terms was found after %d seconds!", StringUtils.getVarargsAsString(terms),
                seconds));
        return null;
    }

    public WebElement getElementContainingTermsInsideElement(boolean asserted, WebElement parentElement, By by, By elementWithText, boolean ignoreCase, int
            seconds, String... terms) {
        Timer timer = new Timer(seconds);
        while (timer.hasMoreTime()) {
            List<WebElement> elements = getElements(asserted, parentElement, by, seconds);
            if (elements != null) {
                implicitlyWaitOneSecond();
                for (WebElement element : elements) {
                    WebElement textContainer = element.findElement(elementWithText);
                    String elementText = textContainer.getText();
                    LOG.debug("elementText: {}", elementText);
                    boolean elementMatches = StringUtils.checkIfTextContainsTerms(elementText, ignoreCase, terms);
                    if (elementMatches) {
                        implicitlyWaitDefault();
                        return element;
                    }
                }
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element containing the '%s' terms was found after %d seconds!", StringUtils.getVarargsAsString(terms),
                seconds));
        return null;
    }

    public WebElement getElementContainingTermsInsideElement(boolean asserted, By by, By elementWithText, boolean ignoreCase, int seconds, String... terms) {
        Timer timer = new Timer(seconds);
        while (timer.hasMoreTime()) {
            List<WebElement> elements = getElements(asserted, by, seconds);
            implicitlyWaitOneSecond();
            for (WebElement element : elements) {
                WebElement textContainer = element.findElement(elementWithText);
                String elementText = textContainer.getText();
                LOG.debug("elementText: {}", elementText);
                boolean elementMatches = StringUtils.checkIfTextContainsTerms(elementText, ignoreCase, terms);
                if (elementMatches) {
                    implicitlyWaitDefault();
                    return element;
                }
            }
        }
        implicitlyWaitDefault();
        Assert.assertFalse(asserted, String.format("No element containing the '%s' terms was found after %d seconds!", StringUtils.getVarargsAsString(terms),
                seconds));
        return null;
    }
}
