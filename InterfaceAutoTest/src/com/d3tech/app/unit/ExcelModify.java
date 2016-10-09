package com.d3tech.app.unit;

import java.io.BufferedWriter;

/*
 * 
 * 功能描述： 根据列名对excel文件修改
* 创建人：魏士超
* 创建时间：2016年4月20日 下午3:00:00  
*  
* Excel放在Data文件夹下</p>
* Excel命名方式：测试类名.xls</p>
* Excel的sheet命名方式：测试方法名</p>
* 
* 
 * 
 * 
 * 
 */



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;



public class ExcelModify {

  

    private int      currentRowNo = 0;
    private int      columnNum    = 0;
    private String[] columnnName;
	
    public void modifyxlsvianame(int modifyrownum,String excelname, String sheetname,String modifycolname,String modifytcontent){
    	try {
			ExcelreadJxl exceltemp=new ExcelreadJxl();
			int modifycolnum=exceltemp.readxls(excelname, sheetname, modifycolname);
			String excelpath="data/"+excelname+".xls";
			updateExcel(excelpath, sheetname, modifycolnum, modifyrownum, modifytcontent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public void updateExcel(String excelpath,String sheetname,int col,int row,String value)throws Exception{
        FileInputStream fis=new FileInputStream(excelpath);
        HSSFWorkbook workbook=new HSSFWorkbook(fis);
        HSSFSheet sheet=workbook.getSheet(sheetname);
        HSSFRow r=sheet.getRow(row);
        HSSFCell cell=r.getCell(col);
        //String str1=cell.getStringCellValue();
        cell.setCellValue(value);
        //System.out.println("单元格原来值为"+str1);
        System.out.println("单元格值被更新为"+value);
        fis.close();//关闭文件输入流
        FileOutputStream fos=new FileOutputStream(excelpath);
        workbook.write(fos);
        fos.close();//关闭文件输出流
        workbook.close();
        reCalculating(excelpath);
    }
    
    public void reCalculating(String exlpath){
    	try {
    		File exlFile=new File(exlpath);
			FileInputStream fis=new FileInputStream(exlFile);
			HSSFWorkbook wbo=new HSSFWorkbook(fis);
			FormulaEvaluator evaluator = wbo.getCreationHelper().createFormulaEvaluator();
			for(int sheetNum = 0; sheetNum < wbo.getNumberOfSheets(); sheetNum++) {
			    Sheet sheet = wbo.getSheetAt(sheetNum);
			    for(Row r : sheet) {
			        for(Cell c : r) {
			            if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
			                evaluator.evaluateFormulaCell(c);
			            }
			        }
			    }
			}
			fis.close();//关闭文件输入流
	        FileOutputStream fos=new FileOutputStream(exlFile);
	        wbo.write(fos);
	        fos.close();//关闭文件输出流
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    

   

    
}
