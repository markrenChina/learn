package sun.net.www.protocol.x;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 测试示例 jdk1.8 可用 11不可用
 * 11 中URL#getURLStreamHandler 只有jrt和file 会进入 sun.net.www.protocol的兜底
 */
public class HandlerTest {

    public static void main(String[] args) throws IOException {
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
            @Override
            public URLStreamHandler createURLStreamHandler(String protocol) {
                if (protocol.equals("x")){
                    return new Handler();
                }else return null;
            }
        });
        URL url = new URL("x:///META-INF/resource.properties");//x类似于classpath 的实现
        var in = url.openStream();
        System.out.println(StreamUtils.copyToString(in, StandardCharsets.UTF_8));

    }
}
