package pages;


import java.awt.Window;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class MergeLeadPage extends OpentapsWrappers{

	public MergeLeadPage() {

		if(!verifyTitle("Merge Leads | opentaps CRM")){
			Reporter.reportStep("This is NOT Create Lead page", "FAIL");

		}
	}	
	// Enter the First Lead
	public MergeLeadPage clickFirstWindow() throws InterruptedException{
		System.out.println("HI");
		clickByXpath(prop.getProperty("Lead.MergeLead.clickFirstWindow"));
		Thread.sleep(3000);
		switchToLastWindow();
		Thread.sleep(5000);
		return this;
	}

	public MergeLeadPage  enterFirstLead(String Lead1){
		enterTextByXpath(prop.getProperty("Lead.MergeLead.enterFirstLeadByXpath"), Lead1);
		return this;
	}

	public MergeLeadPage clickFindLeadsForLead1(String Lead1) throws InterruptedException{
		clickByXpath(prop.getProperty("Lead.MergeLead.clickFindLead"));
		Thread.sleep(4000);
		clickByLink(Lead1);
		return this ;
	}

	public MergeLeadPage switchToPrimaryWindow1(){
		switchToPrimaryWindow();
		return this;
	}

	public MergeLeadPage clickSecondWindow() throws InterruptedException{
		System.out.println("HI2");
		
		clickByXpath(prop.getProperty("Lead.MergeLead.clickSecondWindow"));
		Thread.sleep(3000);

		switchToLastWindow();
		Thread.sleep(5000);
		return this;
	}
	public MergeLeadPage enterSecondLead(String Lead2){
		enterTextByXpath(prop.getProperty("Lead.MergeLead.enterFirstLeadByXpath"), Lead2);
		return this;
	}

	public MergeLeadPage clickFindLeadsForLead2(String Lead2) throws InterruptedException{

		clickByXpath(prop.getProperty("Lead.MergeLead.clickFindLead"));
		Thread.sleep(5000);
		clickByLink(Lead2);
		return this;
	}	
	
	public MergeLeadPage clickMergeLead(){
		clickByLink(prop.getProperty("Lead.MergeLead.clickMergeButton"));
		acceptAlert();
		return this;
	}
	
	public MergeLeadPage clickFindLeadToVerifyMerge(){
		clickByLink(prop.getProperty("Lead.MergeLead.clickFindLeadToVerifyMerge"));
		return this;
	}
	
	public MergeLeadPage enterLead1(String Lead1){
		enterByName("id",Lead1);
		return this;
	}
	
	public MergeLeadPage clickFindLeadAfterMerge() throws InterruptedException{
		clickByXpath(prop.getProperty("Lead.MergeLead.clickFindLeadsToVerifyMergedLead"));
		Thread.sleep(5000);
		return this;
	}
	public MergeLeadPage verifyMergeLeadText(){
		verifyTextContainsByXpath(prop.getProperty("Lead.MergeLead.VerifyText"), "No records to display");
		return this;
		
	}
	
	
}


