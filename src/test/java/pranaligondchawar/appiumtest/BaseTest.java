package pranaligondchawar.appiumtest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {
	 
	
	AndroidDriver driver;
    AppiumDriverLocalService service;
    
    public AppiumDriverLocalService startAppiumServer() {
        AppiumDriverLocalService service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:/Users/91844/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();
        return service;
    }

    
    @BeforeClass
    public void setUp() throws MalformedURLException, URISyntaxException {
        service = startAppiumServer();
        
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel35Pranali");
        options.setAutoGrantPermissions(true);
        options.setApp("C:\\Users\\91844\\eclipse-workspace\\appiumtest\\src\\test\\java\\resources\\tastefood.apk");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);   
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
