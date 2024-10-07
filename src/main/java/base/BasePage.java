package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import java.util.Properties;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelReader;









	public class BasePage {
		
		public static WebDriver driver;
		public static Properties config = new Properties();
		public static Properties OR = new Properties();
		public static FileInputStream fis;
		public static Logger log = Logger.getLogger(BasePage.class);
		public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\Excel\\Excel.xlsx");
		public static WebDriverWait wait;
		public static WebElement dropdown;

	

		public BasePage() {

			if (driver == null) {

				PropertyConfigurator
						.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log.properties");

				try {
					fis = new FileInputStream(
							System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					config.load(fis);
					log.info("Config file loaded");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					fis = new FileInputStream(
							System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\or.properties");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					OR.load(fis);
					log.info("OR file loaded");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (config.getProperty("browser").equals("Chrome")) {

					driver = new ChromeDriver();
					log.info("Chrome launched");

				} else if (config.getProperty("browser").equals("Firefox")) {

					driver = new FirefoxDriver();
					log.info("Firefox launched");

				} else if (config.getProperty("browser").equals("ie")) {

					driver = new InternetExplorerDriver();
					log.info("IE launched");

				}

//				driver.get(config.getProperty("testSiteUrl"));
				driver.manage().window().maximize();
				driver.manage().timeouts()
						.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));

				wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));

			}
		}

		public static void click(String key) {
			try {
				if (key.endsWith("_xpath")) {
					driver.findElement(By.xpath(OR.getProperty(key))).click();
					log.info("Clicking on an element :" + key);
				}

				else if (key.endsWith("_id")) {
					driver.findElement(By.id(OR.getProperty(key))).click();
					log.info("Clicking on an element :" + key);
				}
			} catch (Throwable t) {
				log.info("Error while clicking on an element : " + key);
			}
		}

	/*	public static void type(String key, String value) {

			try {

				if (key.endsWith("_xpath")) {
					driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(OR.getProperty(value));
					log.info("Typing in an element :" + key);
				} else if (key.endsWith("_id")) {
					driver.findElement(By.id(OR.getProperty(key))).sendKeys(OR.getProperty(value));
					log.info("Typing in an element :" + key);

				}

			}

			catch (Throwable t) {
				log.info("Error while typing on an element : " + key);
				// log.info(t);
				log.error(t.getMessage());
				t.printStackTrace();
			}
		}*/
		
		public static void type(String key, String value) {

			try {

				if (key.endsWith("_xpath")) {
					driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);
					log.info("Typing in an element :" + key + "having value" + value);
				} else if (key.endsWith("_id")) {
					driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);
					log.info("Typing in an element :" + key + "having value" + value);

				}

			}

			catch (Throwable t) {
				log.info("Error while typing on an element : " + key);
				// log.info(t);
				log.error(t.getMessage());
				t.printStackTrace();
			}
		}

		public static boolean isElementPresent(String key) {

			try {
				if (key.endsWith("_xpath")) {
					driver.findElement(By.xpath(OR.getProperty(key)));
					log.info("Finding the element :" + key);
				}
				return true;

			} catch (Throwable t) {

				log.info("Error while finding the element : " + key+"exception message :" +t.getMessage());
				return false;
			}

		}
		
		 public void select(String key, String value) {
		        try {
		             dropdown = null;
		            if (key.endsWith("_xpath")) {
		                dropdown = driver.findElement(By.xpath(OR.getProperty(key)));
		            } else if (key.endsWith("_id")) {
		                dropdown = driver.findElement(By.id(OR.getProperty(key)));
		            }

		            if (dropdown != null) {
		                log.info("Selecting the element: " + key);
		               
		                wait.until(ExpectedConditions.elementToBeClickable(dropdown));
		                Select select = new Select(dropdown);

		                // Print available options for debugging
		                for (WebElement option : select.getOptions()) {
		                    log.info("Option available: " + option.getText());
		                }

		                select.selectByVisibleText(value);
		                log.info("Selected value: " + value + " for element: " + key);
		            } else {
		                log.error("Dropdown element not found for key: " + key);
		            }
		        } catch (Throwable t) {
		            log.error("Error while selecting the element: " + key, t);
		        }
		    }          
	

	}
