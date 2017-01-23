import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.Iterator;

public class Read {
   
   public static void main(String[] args)
           throws FileNotFoundException, IOException {
      FileInputStream in = new FileInputStream("ggf12 barcode.xlsx");

      XSSFWorkbook workbook = new XSSFWorkbook(in);
      Sheet spreadsheet = workbook.getSheetAt(0);
      Iterator<Row> rowIterator = spreadsheet.iterator();
      Row row;

      while(rowIterator.hasNext()) {
         row = rowIterator.next();
         Iterator<Cell> cellIterator = row.cellIterator();
         while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch(cell.getCellType()) {
               case Cell.CELL_TYPE_NUMERIC:
                  System.out.print(cell.getNumericCellValue() + " \t\t ");
                  break;
               case Cell.CELL_TYPE_STRING:
                  System.out.print(cell.getStringCellValue() + " \t\t ");
                  break;
            }
         }

         System.out.println("");
         System.out.println("");
      }

      in.close();
   }

}
