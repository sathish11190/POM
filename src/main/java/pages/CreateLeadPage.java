package pages;


import utils.Reporter;
import wrappers.OpentapsWrappers;

public class CreateLeadPage extends OpentapsWrappers{

	public CreateLeadPage() {

		if(!verifyTitle("Create Lead | opentaps CRM")){
			Reporter.reportStep("This is NOT Create Lead page", "FAIL");
		}
	}	
	// Enter the company name
	public CreateLeadPage enterCompany(String userdatacompany){
		System.out.println("HIIIIIIIIIIIIIIIIIII");
		enterById(prop.getProperty("Lead.CreateLead.companyID"), userdatacompany);
		return this;
	}

	// Enter the fName
	public CreateLeadPage enterfName(String fName){
		enterById(prop.getProperty("Lead.CreateLead.fNameID"), fName);
		return this;
	}

	// Enter the lName
	public CreateLeadPage enterlName(String fName){
		enterById(prop.getProperty("Lead.CreateLead.lNameID"), fName);
		return this;
	}

	
	
	// Click Login
	public ViewLeadPage clickCreateLeadButton(){
		clickByClassName(prop.getProperty("Lead.CreateLead.clickSubmmit"));
		return new ViewLeadPage();
	}

}


