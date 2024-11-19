Capstone Project Document: Food Delivery App Test Automation with Appium
Project Overview
This capstone project focuses on automating the testing of a fictional food delivery app using Appium. The app includes functionalities like restaurant search, menu browsing, order placement, and real-time order tracking. The testing process involves UI validations and complex end-to-end workflows, ensuring accurate behaviour across scenarios such as user authentication, location-based services, and payment workflows.
Tools and Technologies
•	JDK for Java support
•	NodeJS and NPM for installing Appium
•	Appium server and Appium Desktop for test automation
•	Android Studio for Android SDK and emulator setup
•	ADB and Appium Inspector for device management and element inspection
________________________________________
Project Setup
1. Environment Setup
•	JDK Installation
Download the JDK from Oracle's website, install it, and set up JAVA_HOME as an environment variable.
•	NodeJS and NPM Installation
Download and install NodeJS from NodeJS's website. Then, open Command Prompt as an administrator and install Appium globally:
•	Android Studio Installation
Download and install Android Studio, ensuring the Android Virtual Device (AVD) component is selected. Configure the SDK, including Android SDK Build-tools, Platform-Tools, and Intel x86 Emulator Accelerator. Set ANDROID_HOME as an environment variable with the Android SDK path.
2. Appium Configuration
•	Initialize a project in your preferred language (Java, Python, or JavaScript).
•	Configure desired capabilities in Appium:


{
    "platformName": "Android",
    "deviceName": "Pixel35Pranali",
    "automationName": "UiAutomator2",
    "appPackage": "com.tastefood.mobileapp2 ",
    "appActivity": "com.tastefood.mobileapp2.MainActivity"
}

________________________________________
Setting Up & Basic Functionality Testing
Objectives
•	Configure the Appium environment and test basic app functionalities.
Tasks
1.	Environment Setup
o	Install Android Studio 
o	Set up emulators or physical devices.
o	Verify tools (adb, Appium Inspector) are working.
2.	Appium Project Configuration
o	Initialize the project and define reusable functions for app launch and session management.
3.	Test User Login & Sign-Up
o	Automate tests for user login and sign-up.
o	Validate various login methods (Email/password, Google/Facebook).
o	Test edge cases (empty fields, incorrect credentials).
Deliverables
•	Project structure and configuration.
•	Basic automated test cases for login.

Login: We write four test cases for login 
•	Empty Fields 
•	Password not given
•	 Incorrect credentials
•	 Successful login

Intermediate Functionality Testing
Objectives
•	Test advanced functionalities like menu browsing, adding items to cart, and checkout.
Tasks
1.	Test Restaurant Menu Navigation
o	Automate navigation through restaurant menus and validate menu item display.
2.	Automate Adding Items to Cart
o	Write scripts to add items to the cart with customizations.
o	Verify total prices, quantity, and item details in the cart.
3.	Test Cart and Checkout Workflow
o	Automate checkout, including payment options and promo code application.
o	Validate edge cases like invalid promo codes.
4.	Simulate Order Tracking
o	Write test scripts for order status updates (Order Placed, In Progress, Out for Delivery).
o	Validate notifications are triggered at each stage.
Deliverables
•	Automated test cases for menu navigation, cart management, and checkout.
•	Order tracking and notification simulation scripts.
o	Menu search:
o	Add to cart
•	My cart where check testcases like changeQuantity, verifyCheckOut. 
 
Restaurant Search which checks testcases like
o	SearchBarPresence
o	SearchBarInput
o	ValidSearchResults
o	NoResultsMessage
o	CaseSensitiveSearchUpdates
o	DynamicSearchUpdates
 
Advanced Testing & Edge Cases
Objectives
•	Test edge cases, location-based services, and device-specific scenarios like network conditions.
Tasks
1.	Automate Location-Based Services
o	Write tests to validate location-based functionalities.
o	Test with different user locations and manual entry options.

https://github.com/user-attachments/assets/cecc8cb0-fa04-4c0b-a50e-dbb806eb7ee8



 
