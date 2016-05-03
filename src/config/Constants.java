package config;

public class Constants {
	
	//System Variables
	public static final String Path_TestData = "C://Users//vblazheska//workspace//Keyword Driven Framework//src//dataEngine//DataEngine.xlsx";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
	//Data Sheet Column Numbers - Settings
	public static final int Col_PageObject1 =2;
	public static final int Col_LocatorType =3;
    public static final int Col_LocatorValue =4;
    
  //Data Sheet Column Numbers - Test Steps
  	public static final int Col_RunMode =2;
  	public static final int Col_Result =3; 
    
    //Data Sheet Column Numbers - Test Steps
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1;
	public static final int Col_PageObject =5;
	public static final int Col_ActionKeyword =4;
	public static final int Col_DataSet =3;
	public static final int Col_TestStepResult =7;
		
	// Data Engine Excel sheets
	public static final String Sheet_TestSteps = "TestSteps";
	public static final String Sheet_TestCases = "TestCases";
	public static final String Sheet_Settings = "Settings";
	

}
