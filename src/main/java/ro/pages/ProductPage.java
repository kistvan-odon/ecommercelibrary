package ro.pages;

import org.openqa.selenium.By;
import ro.constants.Time;
import ro.utils.BasePage;
import ro.utils.StringUtils;

/**
 * Created by Istvan on 05.06.2015.
 */
public class ProductPage extends BasePage {

    public void verifyProductName(By by, String... name) {
        String actualName = driver.getTextFromWebElement(by, false, Time.MEDIUM);
        softAssert.assertTrue(StringUtils.checkIfTextContainsTerms(actualName, name), String.format("The name of the product is '%s' but it should contain: %s!", actualName, StringUtils.getVarargsAsString(name)));
    }
}
