package property;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author markrenChina
 */
public class PropertyDemo {

    public static void main(String[] args) throws IOException {
        var settings = new Properties();
        settings.setProperty("width","600.0");
        var out = new FileOutputStream("program.properties");
        settings.store(out,"Program Properties");

        var settings2 = new Properties();
        var in = new FileInputStream("program.properties");
        settings2.load(in);
        String width = settings2.getProperty("width");
        System.out.println(width);

        String javaVersion = System.getProperty("java.version");
        System.out.println(javaVersion);
    }
}
