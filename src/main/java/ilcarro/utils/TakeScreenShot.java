package ilcarro.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TakeScreenShot {

    public static String takeScreenShort(TakesScreenshot screenshot) {
        Path scrShFilePath = screenshot.getScreenshotAs(OutputType.FILE).toPath();
        String scrnShotFileName = LocalDateTime.now().format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd_HH-mm-ss")) +
                ".png";
        Path targetPath = Paths.get("test_logs/screenchorts/" +
                scrnShotFileName);

        try {
            Files.copy(scrShFilePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return scrnShotFileName;
    }

 /* .  private static String createFileName() {
        String currentDate = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
//        String fileName = "test_logs/screenshot/" + currentDate + ".png";

        String fileName = "test_logs/screenshot/" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +
                ".png";

//  .      LocalDateTime localDateTime = LocalDateTime.now();
//  .      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
//  .      String currentDate = localDateTime.format(df);
// or
//  .      String currentDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        return fileName;
    }
  */
}
