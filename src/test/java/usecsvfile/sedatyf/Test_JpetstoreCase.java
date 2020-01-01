package usecsvfile.sedatyf;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.mappers.CsvWithHeaderMapper;

@RunWith(JUnitParamsRunner.class)
public class Test_JpetstoreCase {

	private static ReadCSV reader;
	WebDriver driver;
	static final String filePath = "src/test/resources/realcase_jpetstore.csv";

	@BeforeClass
	// BeforeClass here is mandatory because if you don't have BeforeClass you will
	// instantiate several reader and you will have only the first line of your map
	public static void initialize() {
		try {
			reader = new ReadCSV(filePath);
		} catch (Exception e) {
			System.out.println("Fichier introuvable : " + e);
		}
	}

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	@FileParameters(value = filePath, mapper = CsvWithHeaderMapper.class)
	public void test(int ID, String email, String password) throws IOException {
		driver.get("https://jpetstore.cfapps.io/login");
		WebElement input_email = driver.findElement(By.name("username"));
		
		input_email.sendKeys(reader.getValue("email"));
		
		WebElement input_password = driver.findElement(By.name("password"));
		
		input_password.sendKeys(reader.getValue("password"));
		reader.nextLine();
		
		driver.findElement(By.id("login")).click();
		//assertEquals("Successfully Logged in...", driver.findElement(By.xpath("//h3")).getText());
	}
	
}
