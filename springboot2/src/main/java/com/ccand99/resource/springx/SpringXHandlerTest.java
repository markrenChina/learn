package com.ccand99.resource.springx;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * jdk11 只有jar 才能进入第三个判断用到 java.protocol.handler.pkgs 这个变量
 * jdk11只有通过实现 URLStreamHandlerFactory 才能实现对协议的扩展
 */
public class SpringXHandlerTest  {

    //-Djava.protocol.handler.pkgs=com.ccand99.resource
    public static void main(String[] args) throws IOException {
        Stream.of(args).forEach(System.out::println);
        //协议名为最后的目录名springx
        URL url = new URL("springx:///META-INF/resource.properties");//x类似于classpath 的实现
        var in = url.openStream();
        System.out.println(StreamUtils.copyToString(in, StandardCharsets.UTF_8));

    }
}
