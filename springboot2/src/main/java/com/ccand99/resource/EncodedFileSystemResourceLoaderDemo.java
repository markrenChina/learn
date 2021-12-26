package com.ccand99.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.FileSystemResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * 带有字符编码的{@link FileSystemResourceLoader} 示例
 */
public class EncodedFileSystemResourceLoaderDemo {

    public static void main(String[] args) throws IOException {
        var currentJavaFilePath = "/"+System.getProperty("user.dir")
                + "/src/main/java/com/ccand99/resource/EncodedFileSystemResourceLoaderDemo.java";
        var resourceLoader = new FileSystemResourceLoader();
        var fileSystemResource = resourceLoader.getResource(currentJavaFilePath);
        var encodeResource = new EncodedResource(fileSystemResource,"UTF-8");
        try(Reader reader = encodeResource.getReader()) {
            System.out.println(IOUtils.toString(reader));
        }
    }
}
