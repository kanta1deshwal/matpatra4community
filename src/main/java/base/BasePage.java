package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import java.util.Properties;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
		
		public static  WebDriver driver;
		public static Properties config = new Properties();
		public static Properties OR = new Properties();
		public static FileInputStream fis;
		public static Logger log = Logger.getLogger(BasePage.class);
		public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\Excel\\Excel.xlsx");
		public static WebDriverWait wait;
		public  static WebElement dropdown;

	

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
//						driver.get(config.getProperty("testSiteUrl"));
						driver.manage().window().maximize();
						driver.manage().timeouts()
								.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
						wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
					}
                 
				}
		public static  void click(String key) {
			try {
				if (key.endsWith("_xpath")) {
					try {
						driver.findElement(By.xpath(OR.getProperty(key))).click();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						log.info("Clicking on an element :" + key);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
		        log.info("Received value for " + key + ": " + value); // Log the received value

		       

		        if (key.endsWith("_xpath")) {
		            try {
		                driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            log.info("Typing in an element: " + key + " having value: " + value);
		        } else if (key.endsWith("_id")) {
		            try {
		                driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            log.info("Typing in an element: " + key + " having value: " + value);
		        }
		    } catch (Throwable t) {
		        log.info("Error while typing on an element: " + key);
		        log.error(t.getMessage());
		        t.printStackTrace();
		    }
		}

		public static  boolean isElementPresent(String key) {

			try {
				if (key.endsWith("_xpath")) {
					try {
						driver.findElement(By.xpath(OR.getProperty(key)));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						log.info("Finding the element :" + key);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return true;

			} catch (Throwable t) {

				log.info("Error while finding the element : " + key+"exception message :" +t.getMessage());
				return false;
			}

		}
		
		public WebElement isToastMessagePresent(String key) {
		    try {
		        // Check if locator exists in OR properties file
		        String locator = OR.getProperty(key);
		        if (locator == null) {
		            log.info("Locator not found in OR file: " + key);
		            return null;
		        }

		        By byLocator = null;

		        // Determine the locator type based on the key suffix
		        if (key.endsWith("_xpath")) {
		            byLocator = By.xpath(locator);
		        } else if (key.endsWith("_id")) {
		            byLocator = By.id(locator);
		        } else {
		            log.warn("Unsupported locator type for key: " + key);
		            return null;
		        }

		        // Wait for the toast message (max 5 sec)
		        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));

		        log.info("Toast Message Found: " + toastMessage.getText());
		        System.out.println("ToastMessage: " + toastMessage.getText());

		        return toastMessage;
		    } catch (TimeoutException e) {
		        log.warn("Toast message not found within timeout: " + key);
		    } catch (NoSuchElementException e) {
		        log.warn("Toast message element does not exist: " + key);
		    } catch (Exception e) {
		        log.error("Error while finding the element: " + key + " Exception message: " + e.getMessage());
		    }

		    return null; // Return null if no toast message is found
		}

		
	    // Generic method to get error message for a given field
	    public String getErrorMessage(String key) {
	        try {
	        	if (OR.getProperty(key) == null) {
		            log.info("Locator not found in OR file: " + key);
		            return " ";
		        }

	        	if (key.endsWith("_xpath")) {
		        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(key))));
		        if (errorElement.isDisplayed()) {
	                return errorElement.getText();
	            }
		        
				} else if (key.endsWith("_id")) {
					 WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(OR.getProperty(key))));
				        if (errorElement.isDisplayed()) {
			                return errorElement.getText();
			            }
				}
	            
	        } catch (Exception e) {
	            return ""; // Return empty string if error element is not present
	        }
	        return "";
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
