package webTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class NewTest {
	private WebDriver driver;
	
	

	@Test
	public void testEasy() {
		driver.get("http://3.15.39.104:8080/dockeransible/");
		driver.manage().window().maximize();
		String heading = driver.findElement(By.xpath("//body//h2")).getText();
		Assert.assertTrue(heading.contains("Deployed using Jenkins"));
	}

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}
}
