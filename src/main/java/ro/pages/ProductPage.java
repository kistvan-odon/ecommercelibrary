package ro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ro.constants.Time;
import ro.utils.BasePage;
import ro.utils.StringUtils;

import java.util.List;

/**
 * Created by Istvan on 05.06.2015.
 */
public class ProductPage extends BasePage {

    public static void verifyProductName(By by, String... name) {
        String actualName = driver.getTextFromWebElement(by, false, Time.MEDIUM);
        softAssert.assertTrue(StringUtils.checkIfTextContainsTerms(actualName, name), String.format("The name of the product is '%s' but it should contain: %s!", actualName, StringUtils.getVarargsAsString(name)));
    }

    public static void verifyProductPrice(By by, String... price) {
        String actualPrice = driver.getTextFromWebElement(by, false, Time.MEDIUM);
        softAssert.assertTrue(StringUtils.checkIfTextContainsTerms(actualPrice, price), String.format("The product price is '%s' but it should contain: %s!", actualPrice, StringUtils.getVarargsAsString(price)));
    }

    public static void verifyProductSpec(By specRowSelector, By labelSelector, By specSelector, String label, String... spec) {
        String actualSpec = driver.getElement(true, driver.getElementContainingTermsInsideElement(true, specRowSelector, labelSelector, true, Time.MEDIUM, label), specSelector, Time.MEDIUM).getText();
        softAssert.assertTrue(StringUtils.checkIfTextContainsTerms(actualSpec, spec), String.format("The product '%s' spec is '%s' but it should contain: %s!", label, actualSpec, StringUtils.getVarargsAsString(spec)));
    }

    public static void verifyProductClassifiedSpec(By categorySelector, By categoryTitleSelector, By labelSelector, By specSelector, String category, String label, String... spec) {
        WebElement specsCategory = driver.getElementContainingTermsInsideElement(true, categorySelector, categoryTitleSelector, true, Time.MEDIUM, category);
        List<WebElement> labels = driver.getElements(true, specsCategory, labelSelector, Time.MEDIUM);
        for (int i = 0; i < labels.size(); i++) {
            String currentLabel = labels.get(i).getText();
            if (currentLabel.equalsIgnoreCase(label)) {
                List<WebElement> specs = driver.getElements(true, specsCategory, specSelector, Time.MEDIUM);
                String actualSpec = specs.get(i).getText();
                softAssert.assertTrue(StringUtils.checkIfTextContainsTerms(actualSpec, spec), String.format("The product '%s' spec is '%s' but it should contain: %s!", label, actualSpec, StringUtils.getVarargsAsString(spec)));
                return;
            }
        }
        softAssert.fail(String.format("The '%s' specifications detail of the product was not found!", label));
    }

    public static WebElement getQuestionElement(By questionSelector, String... terms) {
        WebElement question = driver.getElementContainingTerms(false, questionSelector, true, Time.MEDIUM, terms);
        softAssert.assertNotNull(question, String.format("No question containing the following terms was foun: %s!", StringUtils.getVarargsAsString(terms)));
        return question;
    }

    public static void verifyQuestionExists(By questionSelector, String... terms) {
        getQuestionElement(questionSelector, terms);
    }

    public static void verifyAnswerToQuestion(By questionSelector, By seeAnswerSelector, By answerSelector, String... terms) {
        WebElement question = getQuestionElement(questionSelector, terms);
        driver.getElement(true, question, seeAnswerSelector, Time.MEDIUM).click();
        List<WebElement> answers = driver.getElements(true, question, answerSelector, Time.MEDIUM);
        for(WebElement answer : answers) {
            String answerText = answer.getText().trim();

        }
    }
}
