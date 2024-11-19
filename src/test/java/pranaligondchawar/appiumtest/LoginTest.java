package pranaligondchawar.appiumtest;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class LoginTest extends BaseTest{

    
    
    public static String PHONENO_XPATH = "//android.widget.ImageView[contains(@text,'Phone')]";
    public static String PWD_XPATH = "//android.widget.ImageView[@text='Password']";
    public static String LOGIN_XPATH = "//android.widget.Button[@content-desc='Login']";

       
    @Test(priority = 1)
    public void testEmptyFields() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement skipButton =  wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.Button[@content-desc='Skip']")));
        skipButton.click();
        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PHONENO_XPATH)));
        phoneInputField.clear();
        phoneInputField.click();

        driver.findElement(AppiumBy.xpath(LOGIN_XPATH)).click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc='Enter your phone number']")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message for empty fields should be displayed");
    }

    
    @Test(priority = 2)
    public void testNoPassword() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PHONENO_XPATH)));
        phoneInputField.clear();
        phoneInputField.click();
        phoneInputField.sendKeys("8446718684");  
        driver.hideKeyboard();
        driver.findElement(AppiumBy.xpath(LOGIN_XPATH)).click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc=\"Enter your password\"]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Password should be provided.");
    }


    @Test(priority = 3)
    public void testIncorrectCredentials() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PHONENO_XPATH)));
        phoneInputField.click();
        phoneInputField.sendKeys("8446718684");
        
        WebElement phonePwdField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PWD_XPATH)));
        phonePwdField.click();
        phonePwdField.sendKeys("wrongpassword");
        driver.hideKeyboard();
        
        driver.findElement(AppiumBy.xpath(LOGIN_XPATH)).click();
        

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[contains(@content-desc,'Unauthorized')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Invalid credentials");
    }


    @Test(priority = 4)
    public void testSuccessfulLogin() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement phoneInputField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(PHONENO_XPATH)));
        phoneInputField.click();
        phoneInputField.sendKeys("8446718684");
        WebElement phonePwdField = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ImageView[contains(@text,\"Password\")]")));
        phonePwdField.click();
        phonePwdField.sendKeys("Nilima@12");
        driver.hideKeyboard();
        driver.findElement(AppiumBy.xpath(LOGIN_XPATH)).click();

        WebElement userProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc='Hello, Pranali']")));
        Assert.assertTrue(userProfile.isDisplayed(), "User profile should be displayed after successful login");
    }
}
