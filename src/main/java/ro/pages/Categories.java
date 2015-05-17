package ro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.utils.EnhancedDriver;

/**
 * Created by Istvan on 29.03.2015.
 */
public class Categories {

    private static final Logger LOG = LoggerFactory.getLogger(Categories.class);
    private EnhancedDriver driver;

//	private CategoriesSelectors categories;

//	public Categories(EnhancedDriver driver, CategoriesSelectors categories) {
//		this.driver = driver;
//		this.categories = categories;
//	}

    public Categories(EnhancedDriver driver) {
        this.driver = driver;
    }

    public void selectCategory(By categories, By categoryTitle, By subCategories, String... titles) {
        WebElement parentElement = null;
        for (int i = 0; i < titles.length; i++) {
            LOG.debug("i: {}", i);
            String title = titles[i];
            LOG.debug("title: {}", title);
            if (i == 0) {
                parentElement = driver.getElementContainingTermsInsideElement(true, categories, categoryTitle, true, 10, title);
                LOG.debug("parentElement text: {}", parentElement.getText());
                WebElement parentTitle = driver.getElement(true, parentElement, categoryTitle, 5);
                if(i < titles.length - 1){
                    parentTitle.sendKeys("");
                    driver.mouseOver(parentTitle);
                }else {
                    parentTitle.click();
                }
            } else {
                parentElement = driver.getElementContainingTerms(true, parentElement, subCategories, true, 10, title);
                if(i < titles.length - 1){
                    parentElement.sendKeys("");
                }else {
                    parentElement.click();
                }
            }
        }
    }

//    public void selectCategory(By categories, By subCategories, String... titles) {
//        WebElement parentElement = null;
//        By parentSelector = null;
//        for (int i = 0; i < titles.length; i++) {
//            LOG.debug("i: {}", i);
//            String title = titles[i];
//            LOG.debug("title: {}", title);
//            if (i == 0) {
//                parentSelector = categories;
//            } else {
////                we need to re-search for the previous element to avoid getting a StaleElementReferenceException
//                parentElement = driver.getElementContainingTerms(true, parentSelector, true, 10, title);
//                parentElement.sendKeys("");
//                parentSelector = subCategories;
//            }
//            LOG.debug("parentSelector: {}", parentSelector);
//            parentElement = driver.getElementContainingTerms(true, parentSelector, true, 10, title);
//            parentElement.click();
//        }
//    }

//    public void selectCategory(EnhancedDriver driver, By categories, By subCategories, String... titles) {
//        WebElement parentElement = null;
//        for (int i = 0; i < titles.length; i++) {
//            LOG.debug("i: {}", i);
//            String title = titles[i];
//            LOG.debug("title: {}", title);
//            By selector;
//            if (i == 0) {
//                selector = categories;
//            } else {
//                selector = subCategories;
//            }
//            LOG.debug("selector: {}", selector);
//            LOG.debug("title: {}", title);
//            LOG.debug("driver: {}", driver);
//            parentElement = driver.getElementContainingTerms(true, selector, true, 10, title);
//            parentElement.click();
//        }
//    }

//    public void selectCategory(String... titles) {
//        LOG.debug("categories: {}", this.categories);
//        WebElement parentElement = null;
//        for (int i = 0; i < titles.length; i++) {
//            LOG.debug("i: {}", i);
//            String title = titles[i];
//            LOG.debug("title: {}", title);
//            By selector;
//            if (i == 0) {
//                selector = this.categories.categories;
//            } else {
//                selector = this.categories.subCategories;
//            }
//            LOG.debug("selector: {}", selector);
//            LOG.debug("title: {}", title);
//            LOG.debug("driver: {}", driver);
//            parentElement = driver.getElementContainingTerms(true, selector, true, 10, title);
//            parentElement.click();
//        }
//    }
}
