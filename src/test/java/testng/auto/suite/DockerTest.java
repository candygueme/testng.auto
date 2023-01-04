package testng.auto.suite;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import utils.Logs;

public class DockerTest {
	WebDriver driver;

	@BeforeSuite
	public void before() throws MalformedURLException {
		Logs.info("Start Remote web driver with docker");
		String remote_url_chrome = "http://localhost:4444/wd/hub";
		ChromeOptions options = new ChromeOptions();
		driver = new RemoteWebDriver(new URL(remote_url_chrome), options);
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		System.out.println("Started session");

	}

	@Test
	public void testOneDocker() {
		Logs.info("Start the test");
		String search_string = "LambdaTest";
		String exp_title = "Most Powerful Cross Browser Testing Tool Online | LambdaTest";
		
		WebElement search_box = driver.findElement(By.cssSelector("input[name='q']"));
		WebDriverWait wait = new WebDriverWait (driver, 20);
		search_box.sendKeys(search_string);
		search_box.submit();
		WebElement lt_link = driver.findElement(By.xpath("//h3[text()='LambdaTest: Most Powerful Cross Browser Testing Tool Online']"));
		wait.until(ExpectedConditions.elementToBeClickable(lt_link));
		lt_link.click();

		String curr_window_title = driver.getTitle();
		Assert.assertEquals(curr_window_title, exp_title);
	}



	@AfterSuite
	public void close() {
		Logs.info("Driver quited");
		driver.quit();
	}
}
