package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC02_CreateLead extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "chrome";
		dataSheetName 	= "TC02_CreateLead";
		testCaseName 	= "TC02 - Create Lead";
		testDescription = "Creating Lead using POM framework ";
	}
	
	
	@Test(dataProvider="fetchData")
	public void createLead(String username,String password,String CompanyName,String FirstName,String LastName) {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.clickCRMSFAbutton()
		.clickCreateLead()
		.enterCompany(CompanyName)
	    .enterfName(FirstName)
	    .enterlName(LastName)
	    .clickCreateLeadButton()
	    .verifyFirstName(FirstName);
	    
	}
}
