package com.oracle.PSBV.PIA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PSBV_GetData {
	public static String getExcelCellValue(String excelFilePath,String sheetName,int rowIndex,int columnIndex)
	{
		String cellData=null;
		File f = new File(excelFilePath);
		
			try 
			{
				FileInputStream fis=new FileInputStream(f);
				Workbook wb=WorkbookFactory.create(fis);
				Sheet st=wb.getSheet(sheetName);
				Row rw=st.getRow(rowIndex);
				Cell c=rw.getCell(columnIndex);
				cellData=c.toString();
			} 
			catch (FileNotFoundException e) 
			{				
				System.out.println(excelFilePath+" excel not found, please check");
				System.exit(1);
			}
			catch(EncryptedDocumentException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			catch(InvalidFormatException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		
		return cellData;
	}

}
