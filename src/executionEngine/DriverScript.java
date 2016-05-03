package executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;
 
public class DriverScript {
	
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
		
	public static int iPageObject;
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	public static String sLocatorType;
	public static String sLocatorValue;
	
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
	}
	
    public static void main(String[] args) {
    	try {
    		ExcelUtils.opnExcelFile(Constants.Path_TestData);
        	DOMConfigurator.configure("log4j.xml");
    		
    		DriverScript startEngine = new DriverScript();
    		startEngine.execute_TestCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
		
    private void execute_TestCase() throws Exception {
	    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
			    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
			    		getLocatorType(sPageObject);
			    		getLocatorValue(sPageObject);
			    		execute_Actions();
						if(bResult==false){
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);
							break;
							}						
						}
					if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					Log.endTestCase(sTestCaseID);	
						}					
					}
				}
    		}	
    
    // rum all Action Keywords
    private static void execute_Actions() throws Exception {
	
		for(int i=0;i<method.length;i++){
			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sLocatorValue,sLocatorType,sData);
				if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					ActionKeywords.closeBrowser("","","");
					break;
					}
				}
			}
     }
     
    //read from excel file the Locator type for specific page object
     private static void getLocatorType(String pageObject) throws Exception
     {
     	int iTotalPageObjects = ExcelUtils.getRowCount(Constants.Sheet_Settings);
     	String tPageObject;
     	iPageObject=1;
     	for(;iPageObject<iTotalPageObjects;iPageObject++)
     	{
     		tPageObject = ExcelUtils.getCellData(iPageObject, Constants.Col_PageObject1, Constants.Sheet_Settings);
     		if (tPageObject.equals(pageObject))
     		{
     			sLocatorType=ExcelUtils.getCellData(iPageObject, Constants.Col_LocatorType, Constants.Sheet_Settings);
     			break;
     		}
     		
     	}
     	
     }
     
   //read from excel file the Locator value for specific page object
     private static void getLocatorValue(String pageObject) throws Exception
     {
     	int iTotalPageObjects = ExcelUtils.getRowCount(Constants.Sheet_Settings);
     	String tPageObject;
     	iPageObject=1;
     	for(;iPageObject<iTotalPageObjects;iPageObject++)
     	{
     		tPageObject = ExcelUtils.getCellData(iPageObject, Constants.Col_PageObject1, Constants.Sheet_Settings);
     		if (tPageObject.equals(pageObject))
     		{
     			sLocatorValue=ExcelUtils.getCellData(iPageObject, Constants.Col_LocatorValue, Constants.Sheet_Settings);
     			break;
     		}
     		
     	}
     }
     
}