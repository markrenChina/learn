package com.ccand99.resource;

import com.ccand99.resource.util.ResourceUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 自定义 {@link ResourcePatternResolver} 示例
 * @see ResourcePatternResolver
 * @see PathMatchingResourcePatternResolver
 * @see PathMatcher
 */
public class CustomizedResourcePatternResolverDemo {

    public static void main(String[] args) throws IOException {
        //读取当前package 对应的所有的.java文件
        // *.java
        var currentPackagePath = "/"+System.getProperty("user.dir")
                + "/src/main/java/com/ccand99/resource/";
        var locationPattern = currentPackagePath + "*.java";
        var resourcePatternResolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        resourcePatternResolver.setPathMatcher(new JavaFilePathMatcher());
        var resources = resourcePatternResolver.getResources(locationPattern);
        Stream.of(resources).map(ResourceUtils::getContent).forEach(System.out::println);
    }

    static class JavaFilePathMatcher implements PathMatcher {

        @Override
        public boolean isPattern(String path) {
            return path.endsWith(".java");
        }

        @Override
        public boolean match(String pattern, String path) {
            return path.endsWith(".java");
        }

        @Override
        public boolean matchStart(String pattern, String path) {
            return false;
        }

        @Override
        public String extractPathWithinPattern(String pattern, String path) {
            return null;
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
            return null;
        }

        @Override
        public Comparator<String> getPatternComparator(String path) {
            return null;
        }

        @Override
        public String combine(String pattern1, String pattern2) {
            return null;
        }
    }
}
