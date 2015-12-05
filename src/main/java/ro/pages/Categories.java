package ro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.constants.Time;
import ro.utils.BasePage;

/**
 * Created by Istvan on 29.03.2015.
 */
public class Categories extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(Categories.class);
//    private EnhancedDriver driver;

//	private CategoriesSelectors categories;

//	public Categories(EnhancedDriver driver, CategoriesSelectors categories) {
//		this.driver = driver;
//		this.categories = categories;
//	}

//    public Categories(EnhancedDriver driver) {
//        this.driver = driver;
//    }

    public static void selectCategory(By categories, By categoryTitle, By subCategories, String... titles) {
        WebElement parentElement = null;
        for (int i = 0; i < titles.length; i++) {
            LOG.debug("i: {}", i);
            String title = titles[i];
            LOG.debug("title: {}", title);
            if (i == 0) {
                parentElement = driver.getElementContainingTermsInsideElement(true, categories, categoryTitle, true, Time.MEDIUM, title);
                LOG.debug("parentElement text: {}", parentElement.getText());
                WebElement parentTitle = driver.getElement(true, parentElement, categoryTitle, Time.SMALL);
                if(i < titles.length - 1){
                    parentTitle.sendKeys("");
                    driver.mouseOver(parentTitle);
                }else {
                    parentTitle.click();
                }
            } else {
                parentElement = driver.getElementContainingTerms(true, parentElement, subCategories, true, Time.MEDIUM, title);
                if(i < titles.length - 1){
                    parentElement.sendKeys("");
                }else {
                    parentElement.click();
                }
            }
        }
    }
}
