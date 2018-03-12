package RoughWork;
/*package test.philips.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcel {
	public static List<String> getColumnHeaders(String filePath, String tab) throws IOException
    {
           List<String> colNames=new ArrayList<String>();
           Object d = null;
           FileInputStream file = new FileInputStream(new File(filePath));
           HSSFWorkbook workbook = new HSSFWorkbook(file);
           HSSFSheet sheet = workbook.getSheet(tab);
           Iterator<Row> rowIterator = sheet.iterator();
           Iterator<Cell> cellIterator =null;

           
                  Row row = rowIterator.next();
                  cellIterator = row.cellIterator();
                  
                  while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                               d=cell.getStringCellValue();
                               colNames.add((String) d);
                        }
                  
                  return colNames;
                  }
}


*/