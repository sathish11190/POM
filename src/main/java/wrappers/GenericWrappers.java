package wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.poifs.property.Parent;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.Select;

import utils.Reporter;

public class GenericWrappers {

	protected static RemoteWebDriver driver;
	protected static Properties prop;
	public static String sUrl, primaryWindowHandle, sHubUrl, sHubPort;

	public GenericWrappers() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./config.properties")));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will launch only firefox and maximise the browser and set the
	 * wait for 30 seconds and load the url
	 * 
	 * @author Babu - TestLeaf
	 * @param url
	 *            - The url with http or https
	 * 
	 */
	public boolean invokeApp(String browser) {
		boolean bReturn = false;
		try {

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browser);
			dc.setPlatform(Platform.WINDOWS);
			if (browser.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else
				driver = new FirefoxDriver();

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(sUrl);

			primaryWindowHandle = driver.getWindowHandle();
			System.out.println("Parent widnow handle: " + primaryWindowHandle);
			Reporter.reportStep("The browser:" + browser + " launched successfully", "PASS");
			bReturn = true;

		} catch (Exception e) {
			e.printStackTrace();
			Reporter.reportStep("The browser:" + browser + " could not be launched", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using id attribute to
	 * locate
	 * 
	 * @param idValue
	 *            - id of the webelement
	 * @param data
	 *            - The data to be sent to the webelement
	 * @author Babu - TestLeaf
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public boolean enterById(String idValue, String data) {
		boolean bReturn = false;
		try {
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);
			Reporter.reportStep("The data: " + data + " entered successfully in field :" + idValue, "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The data: " + data + " could not be entered in the field :" + idValue, "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will verify the title of the browser
	 * 
	 * @param title
	 *            - The expected title of the browser
	 * @author Babu - TestLeaf
	 */
	public boolean verifyTitle(String title) {
		boolean bReturn = false;
		try {
			if (driver.getTitle().equalsIgnoreCase(title)) {
				Reporter.reportStep("The title of the page matches with the value :" + title, "PASS");
				bReturn = true;
			} else
				Reporter.reportStep(
						"The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"SUCCESS");

		} catch (Exception e) {
			Reporter.reportStep("The title did not match", "FAIL");
		}

		return bReturn;
	}

	/**
	 * This method will verify the given text
	 * 
	 * @param xpath
	 *            - The locator of the object in xpath
	 * @param text
	 *            - The text to be verified
	 * @author Babu - TestLeaf
	 */
	public boolean verifyTextByXpath(String xpath, String text) {
		boolean bReturn = false;
		String sText = driver.findElementByXPath(xpath).getText();
		System.out.println("Text is" +sText);
		if (driver.findElementByXPath(xpath).getText().trim().equalsIgnoreCase(text)) {
			Reporter.reportStep("The text: " + sText + " matches with the value :" + text, "PASS");
			bReturn = true;
		} else {
			Reporter.reportStep("The text: " + sText + " did not match with the value :" + text, "FAIL");
		}

		return bReturn;
	}

	/**
	 * This method will verify the given text
	 * 
	 * @param xpath
	 *            - The locator of the object in xpath
	 * @param text
	 *            - The text to be verified
	 * @author Babu - TestLeaf
	 */
	public boolean verifyTextContainsByXpath(String xpath, String text) {
		boolean bReturn = false;
		String sText = driver.findElementByXPath(xpath).getText();
		System.out.println("Text:"+sText);
		if (driver.findElementByXPath(xpath).getText().trim().contains(text)) {
			Reporter.reportStep("The text: " + sText + " contains the value :" + text, "PASS");
			bReturn = true;
		} else {
			Reporter.reportStep("The text: " + sText + " did not contain the value :" + text, "FAIL");
		}

		return bReturn;
	}

	/**
	 * This method will close all the browsers
	 * 
	 * @author Babu - TestLeaf
	 */
	public void quitBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			Reporter.reportStep("The browser:" + driver.getCapabilities().getBrowserName() + " could not be closed.",
					"FAIL");
		}

	}
	public void switchToPrimaryWindowWithoutSnapShot(){
		try {
			
			Set<String> whndls1 = driver.getWindowHandles();
			Iterator<String> itr1 = whndls1.iterator();
			//Capture first window handle
			String firstwin1 = itr1.next();
			System.out.println("Parent Window handle::"+firstwin1);
		    driver.switchTo().window(firstwin1);	

			//	String parentwindow = driver.getWindowHandle();
				//driver.switchTo().window(primaryWindowHandle);
				//Reporter.reportStep("Switching to parent window with session id: " + primaryWindowHandle, "PASS");
			} catch (Exception e) {
				//Reporter.reportStep("The switch to parent window with session id: " + driver.getWindowHandle()
			//			+ " could not be performed.", "FAIL");
			}
	}



public void switchToLastWindowWithoutSnapshot(){
		try{
			Set <String> eachwindow = driver.getWindowHandles();
			Iterator<String> itr1 = eachwindow.iterator();
			//Capture first window handle
			String firstwin1 = itr1.next();
			//Capture next window handle
			String nextwin1 = itr1.next();
			driver.switchTo().window(nextwin1);
			
	}  catch (Exception e) {
		//Reporter.reportStep("Switching to last window could not be performed.", "FAIL");
	}

	}


	/**
	 * This method will click the element using id as locator
	 * 
	 * @param id
	 *            The id (locator) of the element to be clicked
	 * @author Babu - TestLeaf
	 */
	public boolean clickById(String id) {
		boolean bReturn = false;
		try {
			driver.findElement(By.id(id)).click();
			Reporter.reportStep("The element with id: " + id + " is clicked.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The element with id: " + id + " could not be clicked.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will click the element using id as locator
	 * 
	 * @param id
	 *            The id (locator) of the element to be clicked
	 * @author Babu - TestLeaf
	 */
	public boolean clickByClassName(String classVal) {
		boolean bReturn = false;
		try {
			driver.findElement(By.className(classVal)).click();
			Reporter.reportStep("The element with class Name: " + classVal + " is clicked.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The element with class Name: " + classVal + " could not be clicked.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will click the element using name as locator
	 * 
	 * @param name
	 *            The name (locator) of the element to be clicked
	 * @author Babu - TestLeaf
	 */
	public boolean clickByName(String name) {
		boolean bReturn = false;
		try {
			driver.findElement(By.name(name)).click();
			Reporter.reportStep("The element with name: " + name + " is clicked.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The element with name: " + name + " could not be clicked.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will click the element using link name as locator
	 * 
	 * @param name
	 *            The link name (locator) of the element to be clicked
	 * @author Babu - TestLeaf
	 */
	public boolean clickByLink(String name) {
		boolean bReturn = false;
		try {
			driver.findElement(By.linkText(name)).click();
			Reporter.reportStep("The element with link name: "+name+" is clicked.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The element with link name: " + name + " could not be clicked.", "FAIL");
		}
		return bReturn;
	}
	
	public boolean clickByLinkWithoutSnapshot(String name) {
		boolean bReturn = false;
		try {
			driver.findElement(By.linkText(name)).click();
		//	Reporter.reportStep("The element with link name: "+name+" is clicked.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			//Reporter.reportStep("The element with link name: " + name + " could not be clicked.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will click the element using xpath as locator
	 * 
	 * @param xpathVal
	 *            The xpath (locator) of the element to be clicked
	 * @author Babu - TestLeaf
	 */
	public boolean clickByXpath(String xpathVal) {
		boolean bReturn = false;
		try {
			driver.findElement(By.xpath(xpathVal)).click();
			Reporter.reportStep("The element : " + xpathVal + " is clicked.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The element with xpath: " + xpathVal + " could not be clicked.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will mouse over on the element using xpath as locator
	 * 
	 * @param xpathVal
	 *            The xpath (locator) of the element to be moused over
	 * @author Babu - TestLeaf
	 */
	public boolean mouseOverByXpath(String xpathVal) {
		boolean bReturn = false;
		try {
			new Actions(driver).moveToElement(driver.findElement(By.xpath(xpathVal))).build().perform();
			Reporter.reportStep("The mouse over by xpath : " + xpathVal + " is performed.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The mouse over by xpath : " + xpathVal + " could not be performed.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will mouse over on the element using link name as locator
	 * 
	 * @param xpathVal
	 *            The link name (locator) of the element to be moused over
	 * @author Babu - TestLeaf
	 */
	public boolean mouseOverByLinkText(String linkName) {
		boolean bReturn = false;
		try {
			new Actions(driver).moveToElement(driver.findElement(By.linkText(linkName))).build().perform();
			Reporter.reportStep("The mouse over by link : " + linkName + " is performed.", "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The mouse over by link : " + linkName + " could not be performed.", "FAIL");
		}
		return bReturn;
	}

	public String getTextByXpath(String xpathVal) {
		String bReturn = "";
		try {
			return driver.findElement(By.xpath(xpathVal)).getText();
		} catch (Exception e) {
			Reporter.reportStep("The element with xpath: " + xpathVal + " could not be found.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will select the drop down value using id as locator
	 * 
	 * @param id
	 *            The id (locator) of the drop down element
	 * @param value
	 *            The value to be selected (visibletext) from the dropdown
	 * @author Babu - TestLeaf
	 */
	public boolean selectById(String id, String value) {
		boolean bReturn = false;
		try {
			new Select(driver.findElement(By.id(id))).selectByVisibleText(value);
			;
			Reporter.reportStep("The element with id: " + id + " is selected with value :" + value, "PASS");

			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The value: " + value + " could not be selected.", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will select the drop down value using id as locator
	 * 
	 * @param id
	 *            The id (locator) of the drop down element
	 * @param index
	 *            The value to be selected (index) from the dropdown
	 * @author Chanakyan - TestLeaf
	 */

	public boolean selectValueByIndexUsingID(String index, String id) {
		boolean bReturn = false;
		try {
			int index1=Integer.parseInt(index);
			new Select(driver.findElement(By.id(id))).selectByIndex(index1);
			;
			Reporter.reportStep("The element with id: " + id + " is selected with index :" + index, "PASS");

			bReturn = true;
		} catch (Exception e) {
			Reporter.reportStep("The value at index: " + index + " could not be selected.", "FAIL");
		}
		return bReturn;

	}

	public void loadObjects() throws FileNotFoundException, IOException {
		prop = new Properties();
		prop.load(new FileInputStream(new File("./object.properties")));

	}

	/**
	 * This method will switch to parent/primary window
	 * 
	 * @author Chanakyan - TestLeaf
	 */
	public void switchToPrimaryWindow() {
		try {

		//	String parentwindow = driver.getWindowHandle();
			driver.switchTo().window(primaryWindowHandle);
		//	Reporter.reportStep("Switching to parent window with session id: " + primaryWindowHandle, "PASS");
		} catch (Exception e) {
			//Reporter.reportStep("The switch to parent window with session id: " + driver.getWindowHandle()
				//	+ " could not be performed.", "FAIL");
		}
	}

	/**
	 * This method will switch to last window
	 * 
	 * @author Chanakyan - TestLeaf
	 */
	public void switchToLastWindow() {
		try {
			Set<String> eachwindow = driver.getWindowHandles();

			for (String lastwindow : eachwindow) {
				driver.switchTo().window(lastwindow);
			}
		//	Reporter.reportStep(
			//		"Switching to last window with session id: " + driver.getWindowHandle() + " is performed", "PASS");

		} catch (Exception e) {
			//Reporter.reportStep("Switching to last window could not be performed.", "FAIL");
		}

	}

	/**
	 * This method will switch to a frame using a webelement
	 * 
	 * @param xpath
	 *            The Xpath of the frame
	 * @author Chanakyan - TestLeaf
	 */

	public void switchToFrameByElementUsingXpath(String xpath) {
		try {
			driver.switchTo().frame(driver.findElementByXPath(xpath));
			Reporter.reportStep("Switching to frame with Xpath: " + xpath + " is performed", "PASS");
		} catch (Exception e) {
			Reporter.reportStep("Switching to frame with Xpath: " + xpath + " could not be performed", "FAIL");
		}
	}

	/**
	 * This method will switch to a frame using an index
	 * 
	 * @param index
	 *            The index of the frame
	 * @author Chanakyan - TestLeaf
	 */

	public void switchToFrame(int index) {
		try {
			driver.switchTo().frame(index);
			System.out.println("Switched to the requried frame " + index);
			Reporter.reportStep("Switching to frame with index: " + index + " is performed", "PASS");
		} catch (Exception e) {
			Reporter.reportStep("Switching to frame with index: " + index + " could not be performed", "FAIL");
		}
	}

	/**
	 * This method will switch to first frame
	 * 
	 * @author Chanakyan - TestLeaf
	 */

	public void switchToFirstFrame() {
		try {
			driver.switchTo().frame(0);
			Reporter.reportStep("Switching to first frame is performed", "PASS");
		} catch (Exception e) {
			Reporter.reportStep("Switching to first frame could not be performed", "FAIL");
		}
	}

	/**
	 * This method will accept the alert
	 * 
	 * @author Chanakyan - TestLeaf
	 */
	public void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
			Reporter.reportStep("Accepting alert window is performed", "PASS");
		} catch (Exception e) {
			Reporter.reportStep("Accepting alert window could not be performed", "FAIL");
		}
	}

	/**
	 * This method will dismiss the alert
	 * 
	 * @author Chanakyan - TestLeaf
	 */
	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			Reporter.reportStep("Dismissing alert window is performed", "PASS");
		} catch (Exception e) {
			Reporter.reportStep("Dismissing alert window could not be performed", "FAIL");
		}
	}

	/**
	 * This method will verify the current url of the browser
	 * 
	 * @param url
	 *            - The expected url of the browser
	 * @return returns boolean value - stating whether the url is same as the
	 *         passed one or not
	 * @author Chanakyan - TestLeaf
	 */

	public boolean verifyUrl(String url) {
		boolean bReturn = false;

		try {
			if (driver.getCurrentUrl().equalsIgnoreCase(url)) {
				Reporter.reportStep("The url of the page matches with the value :" + url, "PASS");
				bReturn = true;
			} else
				Reporter.reportStep(
						"The title of the page:" + driver.getTitle() + " did not match with the value :" + url,
						"SUCCESS");

		} catch (Exception e) {
			Reporter.reportStep("The url did not match", "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using name attribute
	 * to locate
	 * 
	 * @param nameValue
	 *            - name of the webelement
	 * @param value
	 *            - The data to be sent to the webelement
	 * @author Chanakyan - TestLeaf
	 * @throws IOException
	 * @throws COSVisitorException
	 */

	public boolean enterByName(String name, String value) {
		boolean bReturn = false;

		try {
			driver.findElementByName(name).clear();
			driver.findElementByName(name).sendKeys(value);
			Reporter.reportStep("The data: " + value + " entered successfully in field :" + name, "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The data: " + value + " could not be entered in the field :" + name, "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using class attribute
	 * to locate
	 * 
	 * @param classname
	 *            - classname of the webelement
	 * @param value
	 *            - The data to be sent to the webelement
	 * @author Chanakyan - TestLeaf
	 * @throws IOException
	 * @throws COSVisitorException
	 */

	public boolean enterByClassName(String classname, String value) {
		boolean bReturn = false;

		try {
			driver.findElementByClassName(classname).clear();
			driver.findElementByClassName(classname).sendKeys(value);
			Reporter.reportStep("The data: " + value + " entered successfully in field :" + classname, "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The data: " + value + " could not be entered in the field :" + classname, "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will verify the value using id attribute to locate
	 * 
	 * @param id
	 *            - id of the webelement
	 * @param textvalue
	 *            - The data to be verified with the linktext the webelement
	 * @author Chanakyan - TestLeaf
	 */

	public boolean verifyTextByID(String textvalue, String id) {
		boolean bReturn = false;
		try {
			if (driver.findElementById(id).getText().contains(textvalue))
				Reporter.reportStep("The linktext matches with the given value :" + textvalue, "PASS");
			bReturn = true;

		} catch (Exception e) {
			Reporter.reportStep("The linktext not matches with the given value :" + textvalue, "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will enter the value to the text field using xpath attribute
	 * to locate
	 * 
	 * @param xpath
	 *            - xpath of the webelement
	 * @param value
	 *            - The data to be sent to the webelement
	 * @author Chanakyan - TestLeaf
	 * @throws IOException
	 * @throws COSVisitorException
	 */

	public boolean enterTextByXpath(String xpath, String value) {
		boolean bReturn = false;
		try {
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(value);
			Reporter.reportStep("The data: " + value + " entered successfully in field :" + xpath, "PASS");
			bReturn = true;
		} catch (Exception e) {
			Reporter.reportStep("The data: " + value + " could not be entered in the field :" + xpath, "FAIL");
		}
		return bReturn;
	}

	/**
	 * This method will get a text using name attribute to locate
	 * 
	 * @param name
	 *            - name of the webelement
	 * @author Chanakyan - TestLeaf
	 */

	public String getTextByName(String name) {
		String textvalue = "";
		try {
			textvalue = driver.findElementByName(name).getText();
			Reporter.reportStep(
					"The text found :" + driver.findElementByName(name).getText() + " for the given name " + name,
					"PASS");
		} catch (Exception e) {
			Reporter.reportStep(
					"The text not found :" + driver.findElementByName(name).getText() + " for the given name " + name,
					"FAIL");
		}
		return textvalue;
	}

	/**
	 * This method will get a text using id attribute to locate
	 * 
	 * @param id
	 *            - id of the webelement
	 * @author Chanakyan - TestLeaf
	 */
	public String getTextById(String id) {
		String idText = null;
		try {
			idText = driver.findElement(By.id(id)).getText();
			Reporter.reportStep(
					"The text found :" + driver.findElement(By.id(id)).getText() + " for the given name " + id, "PASS");
		} catch (Exception e) {
			Reporter.reportStep(
					"The text not found :" + driver.findElement(By.id(id)).getText() + " for the given name " + id,
					"FAIL");
		}

		return idText;
	}

	/**
	 * This method will get a text using xpath attribute to locate
	 * 
	 * @param xpath
	 *            - xpath of the webelement
	 * @author Chanakyan - TestLeaf
	 */

	public String getValueByXpath(String xpath) {
		String id = null;
		try {
			id = driver.findElement(By.xpath(xpath)).getText();
			Reporter.reportStep(
					"The text found :" + driver.findElement(By.xpath(xpath)).getText() + " for the given name " + xpath,
					"PASS");
		} catch (Exception e) {
			Reporter.reportStep("The text not found :" + driver.findElement(By.xpath(xpath)).getText()
					+ " for the given name " + xpath, "FAIL");
		}
		return id;
	}
	
	public void threadSleep() throws InterruptedException{
		Thread.sleep(4000);
	}

	/*	*//**
			 * This method will give the Substring of a value
			 * 
			 * @author Chanakyan - TestLeaf
			 *//*
			 * 
			 * public void getTextPrint() { String val =
			 * getid("viewLead_companyName_sp"); Reporter.reportStep(
			 * "The text found :" + val.substring(val.indexOf("(")+1,
			 * val.indexOf(")")), "PASS");
			 * 
			 * } To get the string from excel
			 * 
			 * @param string-string
			 * 
			 * private String getid(String string) { // TODO Auto-generated
			 * method stub return null; } To login the application
			 * 
			 * @param fileName-fileName of the excel to be read
			 * 
			 * public void login(String fileName) throws Exception{ String [][]
			 * login = readInput(fileName);
			 * 
			 * enterTextById(login[0][0],"username");
			 * enterTextById(login[0][1],"password");
			 * clickButtonByClassname("decorativeSubmit"); } To handle windows
			 * 
			 * @param parentWindow-Xpath of the webelement to get the text
			 * 
			 * @param expectedText- text value to be compared with
			 * 
			 * public void handleWindows(String parentWindow){ Set<String>
			 * wHandles=driver.getWindowHandles();
			 * 
			 * 
			 * for (String CurrentPopUpWindow : wHandles) { if
			 * (!CurrentPopUpWindow .equals(parentWindow)) {
			 * driver.switchTo().window(CurrentPopUpWindow);
			 * 
			 * } } }
			 */
}
