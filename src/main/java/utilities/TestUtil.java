package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasePage;






public class TestUtil extends BasePage {

	public static String fileName;
	public static Connection con = null; // sql
	private static Connection conn = null; // mysql

	public static void captureScreenShot() throws IOException {
		try {
			Thread.sleep(5000);
			File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			fileName = Date();

			FileUtils.copyFile(screenShot,
					new File(System.getProperty("user.dir") + "\\test-output\\" + fileName));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// File screenShot = ((TakesScreenshot)
		// driver).getScreenshotAs(OutputType.FILE);
		// fileName = Date();

		// FileUtils.copyFile(screenShot, new File(System.getProperty("user.dir") +
		// "\\test-output\\" + fileName + ".jpg"));

	}

	public static String Date() {

		Date d = new Date();
		String FileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		System.out.println(FileName);
		return FileName;
	}

	@DataProvider(name="dp")
	public static Object[][] getData(Method method) {

		// Get the sheet name from the test method annotation
	    String sheetName = method.getAnnotation(Test.class).description();
		
		if (excel == null) {
			

			excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\Excel\\Excel.xlsx");

		}

		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		List<Object[]> dataList = new ArrayList<>();

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			boolean isRowValid = true;
			Object[] dataRow = new Object[cols];

			for (int colNum = 0; colNum < cols; colNum++) {
				String cellData = excel.getCellData(sheetName, colNum, rowNum);

				if (cellData == null || cellData.trim().isEmpty()) {
					isRowValid = false;
					break;
				}

				dataRow[colNum] = cellData;
			}

			if (isRowValid) {
				dataList.add(dataRow);
			}
		}

		return dataList.toArray(new Object[0][0]);
	}

	// SQL Server
	public static void setDbConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(config.getProperty("driver"));
			con = DriverManager.getConnection(config.getProperty("dbConnectionUrl"), config.getProperty("dbUserName"),
					config.getProperty("dbPassword"));

			if (!con.isClosed())
				System.out.println("Successfully connected to SQL server");

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());

			// monitoringMail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to,
			// TestConfig.subject+" - (Script failed with Error, Datamart database used for
			// reports, connection not established)", TestConfig.messageBody,
			// TestConfig.attachmentPath, TestConfig.attachmentName);
		}

	}

	public static void setMysqlDbConnection() throws SQLException, ClassNotFoundException {
		try {

			Class.forName(config.getProperty("mysqldriver"));
			conn = DriverManager.getConnection(config.getProperty("mysqlurl"), config.getProperty("mysqlusername"),
					config.getProperty("mysqlpassword"));
			if (!conn.isClosed())
				System.out.println("Successfully connected to MySQL server");

		} catch (Exception e) {
			System.err.println("Cannot connect to database server");

			// monitoringMail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to,
			// TestConfig.subject+" - (Script failed with Error, Datamart database used for
			// reports, connection not established)", TestConfig.messageBody,
			// TestConfig.attachmentPath, TestConfig.attachmentName);
		}

	}

	public static List<String> getQuery(String query) throws SQLException {

		// String Query="select top 10* from ev_call";
		Statement St = con.createStatement();
		ResultSet rs = St.executeQuery(query);
		List<String> values = new ArrayList<String>();
		while (rs.next()) {

			values.add(rs.getString(1));

		}
		return values;
	}

	public static void Logout() {
		Actions action = new Actions(driver);

		

		action.moveToElement( driver.findElement(By.xpath(OR.getProperty("LogOut")))).build().perform();

		driver.findElement(By.xpath("//div[text()='Logout']")).click();
		log.info("Checking the via_Password tab presence "); 
		Boolean loggedOutSuccessfully = isElementPresent("via_Password_xpath");
		if (loggedOutSuccessfully) {
			log.info("Clicked on logout");
		} else {
			log.info("User is not able to logged out: ");
			Assert.fail("Unable to locate logout button: ");
		}

	}

	public static void setZoomLevel(double zoomLevel) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.body.style.zoom='" + zoomLevel + "'");
	}

	public static String getMysqlQuery(String query) throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		String result = "";

		if (rs.next()) {
			result = rs.getString(1);
		}
		System.out.println("Query result is " + result);

		return result;
	}

	public static Connection getConnection() {
		return con;
	}
	
	
	
}
