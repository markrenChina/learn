package i18n;

import java.nio.charset.Charset;

/**
 * @author markrenChina
 */
public class EncodingDemo {

    public static void main(String[] args) {
        Charset platformEncoding = Charset.defaultCharset();
        System.out.println(platformEncoding);
    }
}
