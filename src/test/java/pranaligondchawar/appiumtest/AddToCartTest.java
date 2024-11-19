package pranaligondchawar.appiumtest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AddToCartTest extends AuthenticatedTests {
	
	public static String SEARCHIP_XPATH = "//android.widget.EditText[contains(@text,\"Search\")]";
	public static String CANCEL_XPATH = "//android.widget.Button[@content-desc=\"Cancel\"]";
	public static String SUGGESTION_XPATH = "//android.view.View[@content-desc=\"Suggestions\"]";
	public static String ADDTOCART_XPATH = "//android.widget.Button[@content-desc=\"Add To Cart\"]";
	
	
	
	@Test(priority = 1, description = "Add cart button is visible")
	public void addToCartButtonVisible() throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		searchFoodItem("Cake", 1);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[contains(@content-desc,\"Cake\")]"))).click();
	  
    	WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(ADDTOCART_XPATH)));
    	Assert.assertTrue(addToCartButton.isDisplayed(), "Add to cart button is not visible");
    	    
	}

	
    @Test(priority = 2, description="Verify item added to cart and checkout button is visible")
    public void addingToCart() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
    	WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(ADDTOCART_XPATH)));
    	addToCartButton.click();
    	
    	//clear earlier search results
    	driver.findElement(AppiumBy.xpath(CANCEL_XPATH)).click();
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SUGGESTION_XPATH)));
    	driver.findElement(AppiumBy.xpath(CANCEL_XPATH)).click();
    	
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.view.View[@content-desc='Hello, Pranali']")));
    	WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[@content-desc=\"1\"]")));
    	Assert.assertTrue(cartButton.isDisplayed(), "Cart button is not visible");
    	cartButton.click();
    	
    	wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.view.View[@content-desc=\"My Cart\"]")));
    	WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Proceed to Checkout\"]")));
    	Assert.assertTrue(checkoutBtn.isDisplayed(), "Checkout button is not visible");
    }

    @Test(priority = 3,description="Verify if quantity can be increased and decreased")
    public void changeQuantity() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	
    	//plus button click
    	driver.findElement(AppiumBy.xpath("//android.widget.ImageView[contains(@content-desc,\"Cake\")]/android.view.View[2]")).click();
    	
    	//verify total increased
    	WebElement totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("(//android.view.View[contains(@content-desc,\"20\")])[2]")));
    	Assert.assertTrue(totalPrice.isDisplayed(), "Total price not increased after clicking plus");
    	
    	//plus button click
    	driver.findElement(AppiumBy.xpath("//android.widget.ImageView[contains(@content-desc,\"Cake\")]/android.view.View[1]")).click();
    	
    	//verify total increased
    	WebElement totalPrice2 = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("(//android.view.View[contains(@content-desc,\"10\")])[2]")));
    	Assert.assertTrue(totalPrice2.isDisplayed(), "Total price not decreased after clicking minus");
    	
    }
    

    @Test(priority = 4,description="Verify checkout page and confirm order")
    public void verifyCheckOut() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Proceed to Checkout\"]")));
    	checkoutBtn.click();
    	
    	WebElement deliverTo = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"Deliver To\"]")));
    	Assert.assertTrue(deliverTo.isDisplayed(), "Delivery page is not visible");
    	
                
    	 String scrollCommand = "new UiScrollable(new UiSelector().scrollable(true))" +
                 ".scrollForward().scrollIntoView(new UiSelector().description(\"Confirm Order\"));";
        
        driver.findElement(AppiumBy.androidUIAutomator(scrollCommand));
   
        WebElement confirmOrderButton = driver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc='Confirm Order']"));
        
    }

    
    public void searchFoodItem(String foodItem, int totalItems) throws InterruptedException {
    	
    	WebElement searchBar = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Search your desired foods or restaurants\"]"));
        Assert.assertTrue(searchBar.isDisplayed(), "Search bar is not visible");
    	searchBar.click();
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	
    	wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(SUGGESTION_XPATH)));
    	WebElement searchInput =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(SEARCHIP_XPATH)));
    	Assert.assertTrue(searchInput.isDisplayed(), "Search input is not visible");
    	searchInput.click();
		searchInput.sendKeys(foodItem);
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		WebElement searchResultString =  wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"results found\"]")));
    	Assert.assertTrue(searchResultString.isDisplayed(), "Search Result string is not visible");
    	
    	List<WebElement> foodItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.ImageView[@content-desc]")));
    	Assert.assertEquals(foodItems.size(), totalItems, "Expected food items in the search results does not match");
    }

	
}
