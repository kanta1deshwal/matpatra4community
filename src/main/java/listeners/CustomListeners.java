package listeners;

import java.io.File;
import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestNG;

import utilities.TestUtil;

public class CustomListeners implements ITestListener {

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
       ITestListener.super.onTestFailure(result);
		
		try {
			TestUtil.captureScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Screenshot File Path: " + TestUtil.fileName);

		// Screenshot Link (Text)
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.fileName + "\">Screenshot Link</a>");
		Reporter.log("<br>");

		// Screenshot Thumbnail
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.fileName + "\">"
		            + "<img src=\"" + TestUtil.fileName + "\" height=\"200\" width=\"200\"></a>");
	}
	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
	//	System.setProperty("org.uncommons.reportng.escape-output", "false");
		ITestListener.super.onTestFailure(result);
		
		try {
			TestUtil.captureScreenShot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Screenshot File Path: " + TestUtil.fileName);

		// Screenshot Link (Text)
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.fileName + "\">Screenshot Link</a>");
		Reporter.log("<br>");

		// Screenshot Thumbnail
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.fileName + "\">"
		            + "<img src=\"" + TestUtil.fileName + "\" height=\"200\" width=\"200\"></a>");

		
	}
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}
}