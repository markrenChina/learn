package com.ccand99.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StreamUtils;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

/**
 * 带有字符编码的{@link org.springframework.core.io.FileSystemResource} 示例
 */
public class EncodedFileSystemResourceDemo {

    public static void main(String[] args) throws IOException {
        var currentJavaFilePath = System.getProperty("user.dir")
                + "/src/main/java/com/ccand99/resource/EncodedFileSystemResourceDemo.java";
        var currentJavaFile = new File(currentJavaFilePath);
        var fileSystemResource = new FileSystemResource(currentJavaFile);
        var encodeResource = new EncodedResource(fileSystemResource,"UTF-8");
        try(Reader reader = encodeResource.getReader()) {
            System.out.println(IOUtils.toString(reader));
        }
    }
}
