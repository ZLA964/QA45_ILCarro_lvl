package ilcarro.utils;

///import java.io.InputStream;
///import java.io.FileInputStream;
///import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesReader {

    public static void main(String[] args) {
        System.out.println(getProperty("login.properties", "urlStart"));
    }


    public static String getProperty(String fileName, String key) {
        Properties properties = new Properties();
        Path targetPath = Path.of("src/test/resources/properties/", fileName);
        try (var stream = Files.newInputStream(targetPath)) {
            properties.load(stream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

/*/        try (FileInputStream stream = new FileInputStream("src/test/resources/properties/"+fileName )) {
//            properties.load(stream);
//            return properties.getProperty(key);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
*/

    }
}
