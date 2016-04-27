package pages;


import utils.Reporter;
import wrappers.OpentapsWrappers;

public class ViewLeadPage extends OpentapsWrappers{

	public ViewLeadPage() {

		if(!verifyTitle("View Lead | opentaps CRM")){
			Reporter.reportStep("This is NOT View Lead page", "FAIL");
		}

	}


	public void verifyFirstName(String firstName){
		if(verifyTextByXpath(prop.getProperty("Lead.ViewLead.verifyTextXpath"),firstName )){
			Reporter.reportStep("First Name is correct", "PASS");
			
		}
	}


}
