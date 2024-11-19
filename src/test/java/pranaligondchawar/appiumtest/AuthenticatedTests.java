package pranaligondchawar.appiumtest;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class AuthenticatedTests extends BaseTest {
	 public static String PHONENO_XPATH = "//android.widget.ImageView[contains(@text,'Phone')]";
	 public static String PWD_XPATH = "//android.widget.ImageView[@text='Password']";
	 public static String LOGIN_XPATH = "//android.widget.Button[@content-desc='Login']";
	
	@Override
	@BeforeClass
	public void setUp() throws MalformedURLException, URISyntaxException {
		super.setUp();
		performLogin();
	}
	
	private void performLogin() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        WebElement skipButton =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[@content-desc='Skip']")));
	        skipButton.click();

	        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PHONENO_XPATH)));
	        phoneInputField.click();
	        phoneInputField.sendKeys("8446718684");
	        WebElement phonePwdField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PWD_XPATH)));
	        phonePwdField.click();
	        phonePwdField.sendKeys("Nilima@12");
	        driver.hideKeyboard();
	        driver.findElement(AppiumBy.xpath(LOGIN_XPATH)).click();

	        WebElement userProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc='Hello, Pranali']")));
	        Assert.assertTrue(userProfile.isDisplayed(), "User profile should be displayed after successful login");
	        
	        WebElement locationView = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Google Building 43, 43 Amphitheatre Pkwy, Mountain View, CA 94043, USA\"]"));
			locationView.click();
	
			WebElement homeLocationButton =  wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[contains(@content-desc,\"Home\")]")));
			homeLocationButton.click(); 
			userProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc='Hello, Pranali']")));
		    Assert.assertTrue(userProfile.isDisplayed(), "User profile should be displayed after location set");
	}

}
