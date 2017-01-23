import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;

public class Edit {
   
   public static void main(String[] args)
           throws FileNotFoundException, IOException {
      FileInputStream in = new FileInputStream("workbook.xlsx");

      XSSFWorkbook workbook = new XSSFWorkbook(in);
      Sheet sheet = workbook.getSheetAt(0);

      Row row = sheet.getRow(7);
      Cell cell = row.getCell(12);
      String content = cell.getStringCellValue();
      System.out.println("The cell is currently: " + content);
      System.out.println("Changing to: 'finish edit'");


      cell.setCellValue("finish edit");

      in.close();
      FileOutputStream out = new FileOutputStream("workbook.xlsx");
      workbook.write(out);
      
      out.close();
   }

}
