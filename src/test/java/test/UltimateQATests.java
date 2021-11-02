package test;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;

public class UltimateQATests {
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeTest
	public void setUpTest() {
		
		// start report
        htmlReporter = new ExtentHtmlReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);  
 
        // Set WebDriver
		System.out.println(projectPath);
		System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/geckodriver/geckodriver.exe");
		driver = new FirefoxDriver();

	}

	@Test
	public void launchBrowser() throws Exception {
		ExtentTest test = extent.createTest("Testing UltimateQA website", "Assessment test");
		
     	// Launch browser
		driver.get("https://ultimateqa.com/automation/");
		test.pass("Launch UltimateQA");
		
        // log with snapshot
        test.pass("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
        
        // test with snapshot
        test.addScreenCaptureFromPath("screenshot.png");
		
		test.log(Status.INFO, "Starting Test Case");
		

		//Take screenshort and save to image folder
        this.takeSnapShot(driver, projectPath+"/image/test.png") ;  

		// Muximise browser
		driver.manage().window().maximize();

		// Launch Login page
		driver.findElement(By.xpath("//a[contains(text(),'Login automation')]")).click();
		test.pass("Launch Login page");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Capture Email
		driver.findElement(By.id("user[email]")).sendKeys("zandile.memka@gmail.com");
		test.pass("Enter registered email");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Capture Password
		driver.findElement(By.id("user[password]")).sendKeys("Zandile@11");
		test.pass("Enter rpassword");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Click Sign in
		driver.findElement(By.xpath("//body/main[@id='main-content']/div[1]/div[1]/article[1]/form[1]/div[4]/input[1]")).click();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     

	}

	public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

		// Convert web driver object to TakeScreenshot

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

		// Call getScreenshotAs method to create image file

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination

		File DestFile = new File(fileWithPath);

		// Copy file at destination

		Files.copy(SrcFile, DestFile);

	}

	
	@AfterTest
	public void tearDownTest() {
		
		//Close Browser
		driver.close();
	
        // calling flush writes everything to the log file
        extent.flush();
        
		System.out.println("Test Completed Successfully");
		
	}
}
