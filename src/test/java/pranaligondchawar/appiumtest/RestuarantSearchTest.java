package pranaligondchawar.appiumtest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class RestuarantSearchTest extends AuthenticatedTests {

	public static String SEARCHIP_XPATH = "//android.widget.EditText[contains(@text,\"Search\")]";
	public static String CANCEL_XPATH = "//android.widget.Button[@content-desc=\"Cancel\"]";
	public static String SUGGESTION_XPATH = "//android.view.View[@content-desc=\"Suggestions\"]";
	public static String REST_XPATH ="//android.view.View[contains(@content-desc,\"Restaurants\")]";
	
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
		searchInput.sendKeys("Cafe");
		Assert.assertTrue(searchInput.getText().contains("Cafe"));
    }
    
    @Test(priority = 3)
    public void testValidSearchResults() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
		searchInput.sendKeys("Cafe India");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		WebElement searchResultString =  wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	Assert.assertTrue(searchResultString.isDisplayed(), "Search Result string is not visible");
    	
    	//click on restaurants tab
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(REST_XPATH))).click();
    	
    	//wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	List<WebElement> restList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	Assert.assertEquals(restList.size(), 1, "Expected 1 food items in the search results.");
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

    	//click on restaurants tab
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(REST_XPATH))).click();

    	List<WebElement> restList = driver.findElements(AppiumBy.xpath("//android.widget.ImageView[@content-desc]"));
    	
    	Assert.assertEquals(restList.size(), 0, "Expected 0 food items in the search results.");
    }

    @Test(priority = 6)
    public void testCaseSensitiveSearchUpdates() throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Cancel\"]")).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
		searchInput.click();
		searchInput.sendKeys("cafe india");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		// waiting for search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
		//click on restaurants tab
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(REST_XPATH))).click();

		List<WebElement> restList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	
		
		int smallItemSize = restList.size();
		
    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath(CANCEL_XPATH)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	
		searchInput.click();
		searchInput.sendKeys("CAFE INDIA");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));

		// waiting for search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
		//click on restaurants tab
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(REST_XPATH))).click();

    	restList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
		int capitalItemSize = restList.size();
		
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
		searchInput.sendKeys("cafe");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		// waiting for search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
		//click on restaurants tab
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(REST_XPATH))).click();

		List<WebElement> restList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	
		
		int itemSize = restList.size();
	    Assert.assertEquals(itemSize,1, "Search Result does not work for half word");
    }
    

}
