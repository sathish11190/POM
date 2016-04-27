package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC02_MergeLead extends OpentapsWrappers{

	@BeforeClass
	public void startTestCase(){
		browserName 	= "chrome";
		dataSheetName 	= "TC05_MergeLead";
		testCaseName 	= "TC05 - Merge Lead";
		testDescription = "Merging Lead using POM framework ";
	}


	@Test(dataProvider="fetchData")
	public void createLead(String username,String password,String Lead1,String Lead2) throws InterruptedException {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.clickCRMSFAbutton()
		.clickMergeLead()
		.clickFirstWindow()
		.enterFirstLead(Lead1)
		.clickFindLeadsForLead1(Lead1)
		.switchToPrimaryWindow1()
		.clickSecondWindow()
		.enterSecondLead(Lead2)
		.clickFindLeadsForLead2(Lead2)
		.switchToPrimaryWindow1()
		.clickMergeLead()
		.clickFindLeadToVerifyMerge()
		.enterLead1(Lead1)
		.clickFindLeadAfterMerge()
		.verifyMergeLeadText();
		 

	}
}
