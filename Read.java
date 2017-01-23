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
         Iterator<Cell<T>> cellIterator = row.cellIterator();
         /*while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            switch(cell.getCellType()) {
               case Cell.CELL_TYPE_NUMERIC:
                  System.out.print(cell.getNumericCellValue() + " \t\t ");
                  break;
               case Cell.CELL_TYPE_STRING:
                  System.out.print(cell.getStringCellValue() + " \t\t ");
                  break;
            }
         }*/
         //create a new Plant object
         Plant plant = new Plant();
         plant.barcode = cellIterator.next(); /*saving the barcode is a field we need to add to the plant object struct*/
         plant.botanicalName = cellIterator.next();
         plant.commonName = cellIterator.next();
         plant.size = cellIterator.next();
         plant.details = cellIterator.next();
         
         /*other code to manipulate the data further*/

         /*System.out.println("");
         System.out.println("");*/
      }

      in.close();
   }

}
