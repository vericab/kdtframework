package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

import config.Constants;
import executionEngine.DriverScript;

// Read/Write from/to Excel file
public class ExcelUtils {
                private static XSSFSheet ExcelWSheet;
                private static XSSFWorkbook ExcelWBook;
                private static org.apache.poi.ss.usermodel.Cell Cell;
                private static XSSFRow Row;
                //private static XSSFRow Row;
           
            // Open the excel from specific path 
            public static void opnExcelFile(String Path) throws Exception {
            	try {
                    FileInputStream ExcelFile = new FileInputStream(Path);
                    ExcelWBook = new XSSFWorkbook(ExcelFile);
            	} catch (Exception e){
            		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
            		DriverScript.bResult = false;
                	}
            	}
            
            // Returns data from specific cell            
            public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
                try{
                	ExcelWSheet = ExcelWBook.getSheet(SheetName);
                   	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                    String CellData = Cell.getStringCellValue();
                    return CellData;
                 }catch (Exception e){
                     Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
                     DriverScript.bResult = false;
                     return"";
                     }
                 }
            
            // Returns row's number in specific work sheet
        	public static int getRowCount(String SheetName){
        		int iNumber=0;
        		try {
        			ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			iNumber=ExcelWSheet.getLastRowNum()+1;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			}
        		return iNumber;
        		}
        	
        	// Returns number of steps for specific Test Case
        	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
        		try {
	        		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
	        			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
	        				int number = i;
	        				return number;      				
	        				}
	        			}
	        		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	        		int number=ExcelWSheet.getLastRowNum()+1;
	        		return number;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			return 0;
                }
        	}
        	
        	//Returns the row number from where the specific test case begin       	
        	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
        		int iRowNum=0;	
        		try {
        		    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			int rowCount = ExcelUtils.getRowCount(SheetName);
        			for (; iRowNum<rowCount; iRowNum++){
        				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
        					break;
        				}
        			}       			
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			}
        		return iRowNum;
        		}


        	
        	// Update the excel file, set test status, PASS/FAIL, for specific action      	
        	@SuppressWarnings("static-access")
        	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
                   try{
                	   
                	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
                       Row  = ExcelWSheet.getRow(RowNum);
                       Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
                       if (Cell == null) {
                    	   Cell = Row.createCell(ColNum);
                    	   Cell.setCellValue(Result);
                        } else {
                            Cell.setCellValue(Result);
                        }
                         FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
                         ExcelWBook.write(fileOut);
                         //fileOut.flush();
                         fileOut.close();
                         ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
                     }catch(Exception e){
                    	 DriverScript.bResult = false;
              
                     }
                }

    	}