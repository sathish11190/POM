package pages;


import utils.Reporter;
import wrappers.OpentapsWrappers;

public class CRMHomePage extends OpentapsWrappers{

	public CRMHomePage() {

		if(!verifyTitle("My Home | opentaps CRM")){
			Reporter.reportStep("This is NOT CRMHome page", "FAIL");
		}

	}
	
	public CreateLeadPage clickCreateLead(){
		clickByLink(prop.getProperty("CRMSFA.click.Leads"));
		clickByLink(prop.getProperty("CRMSFA.click.createLead"));
		return new CreateLeadPage();
	}
	
	
	public MergeLeadPage clickMergeLead() throws InterruptedException{
		clickByLink(prop.getProperty("CRMSFA.click.Leads"));
		clickByLink(prop.getProperty("CRMSFA.click.mergeLead"));
		Thread.sleep(1000);
		return new MergeLeadPage();
	}



}


