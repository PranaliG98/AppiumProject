package pranaligondchawar.appiumtest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.AndroidKey;

public class FoodSearchTest extends AuthenticatedTests {
	
	
	public static String SEARCHIP_XPATH = "//android.widget.EditText[contains(@text,\"Search\")]";
	public static String CANCEL_XPATH = "//android.widget.Button[@content-desc=\"Cancel\"]";
	public static String SUGGESTION_XPATH = "//android.view.View[@content-desc=\"Suggestions\"]";
	
	
    @Test(priority = 1)
    public void testSearchBarPresence() {
    	WebElement searchBar = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search your desired foods or restaurants\"]"));
        Assert.assertTrue(searchBar.isDisplayed(), "Search bar is not visible");
    }

    @Test(priority = 2)
    public void testSearchBarInput() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchBar = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.view.View[@content-desc=\"Search your desired foods or restaurants\"]")));
    	searchBar.click();
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SUGGESTION_XPATH)));
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
		searchInput.sendKeys("noodles");
		Assert.assertTrue(searchInput.getText().contains("noodles"));
    }
    
    @Test(priority = 3)
    public void testValidSearchResults() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
		searchInput.sendKeys("noodles");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		WebElement searchResultString =  wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	Assert.assertTrue(searchResultString.isDisplayed(), "Search Result string is not visible");
    	
    	
    	List<WebElement> foodItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	Assert.assertEquals(foodItems.size(), 2, "Expected 2 food items in the search results.");
    }

    @Test(priority = 4)
    public void testNoResultsMessage() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath(CANCEL_XPATH)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
    	searchInput.sendKeys("XYZ123");
    	driver.pressKey(new KeyEvent(AndroidKey.ENTER));


    	//waiting till search results are loaded
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	List<WebElement> foodItems = driver.findElements(AppiumBy.xpath("//android.widget.ImageView[@content-desc]"));
    	
    	Assert.assertEquals(foodItems.size(), 0, "Expected 0 food items in the search results.");
    }

    @Test(priority = 6)
    public void testCaseSensitiveSearchUpdates() throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Cancel\"]")).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
		searchInput.sendKeys("noodle");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		// waiting for search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	List<WebElement> foodItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	
		
		int smallItemSize = foodItems.size();
		
    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath(CANCEL_XPATH)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	
		searchInput.click();
		searchInput.sendKeys("NOODLE");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));

		// waiting for search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	foodItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
		int capitalItemSize = foodItems.size();
		
    	Assert.assertEquals(smallItemSize,capitalItemSize, "Search Result are not case insensitive");
    }
    
    
    @Test(priority = 7)
    public void testDynamicSearchUpdates() throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath(CANCEL_XPATH)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
		searchInput.sendKeys("noodl");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		// waiting for search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	List<WebElement> foodItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	
		
		int itemSize = foodItems.size();
	    Assert.assertEquals(itemSize,3, "Search Result does not work for half word");
    }
    
   
    
}
