import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;

public class Create {
   
   public static void main(String[] args)
           throws FileNotFoundException, IOException {
      FileOutputStream out = new FileOutputStream("workbook.xls");

      Workbook workbook = new HSSFWorkbook();
      Sheet sheet = workbook.createSheet("Sample sheet");

      Row row = sheet.createRow(7);
      Cell cell = row.createCell(12);
      cell.setCellValue("Blahblah");

      workbook.write(out);
      out.close();
   }

}
