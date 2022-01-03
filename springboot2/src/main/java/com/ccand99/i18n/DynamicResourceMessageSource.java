package com.ccand99.i18n;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import java.awt.font.NumericShaper;
import java.io.*;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.stream.IntStream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * 动态更新资源 {@link AbstractMessageSource} 实现
 *
 * 实现步骤：
 * 1. 定位资源位置
 * 2. 初始化 Properties 对象
 * 3. 实现 AbstractMessageSource#resolveCode 方法
 * 4. 监听资源文件（Java NIO 2 WatchService）
 * 5. 使用线程池处理文件变化
 * 6. 重新装载 Properties 对象
 *
 * @see org.springframework.context.MessageSource
 * @see AbstractMessageSource
 */
public class DynamicResourceMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private static final String resourceFileName = "messages.properties";

    private static final  String resourcePath = "/META-INF/" + resourceFileName;

    private static final String ENCODING = "UTF-8";

    private final Resource messagesPropertiesResource;

    private final ExecutorService executorService;

    private final Properties messageProperties;
    private ResourceLoader resourceLoader;

    public DynamicResourceMessageSource() {
        messagesPropertiesResource = getMessagePropertiesResource();
        messageProperties = loadMessageProperties();
        executorService = Executors.newSingleThreadExecutor();
        //监听资源文件（Java NIO 2 WatchService）
        onMessagePropertiesChanged();
    }

    private void onMessagePropertiesChanged() {
        if (this.messagesPropertiesResource.isFile()){ //判断是否为文件
            try {
                File messagePropertiesFile = messagesPropertiesResource.getFile();
                Path messagePropertiesFilePath = messagePropertiesFile.toPath();
                //获取当前OS的文件系统
                FileSystem fileSystem = FileSystems.getDefault();
                // 新建 WatchService
                WatchService watchService = fileSystem.newWatchService();
                //获取资源文件所在的目录
                Path dirPath = messagePropertiesFilePath.getParent();
                // 注册 WatchService 到messagePropertiesFilePath, 并且关心修改事件
                dirPath.register(watchService, ENTRY_MODIFY);
                processMessagePropertiesChanged(watchService);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理资源文件变化 （异步）
     * @param watchService
     */
    private void processMessagePropertiesChanged(WatchService watchService){
        executorService.submit(()->{
            while (true){
                WatchKey watchKey = watchService.take(); //take 发生阻塞
                //判断watchKey 是否有效
                try {
                    if (watchKey.isValid()) {
                        watchKey.pollEvents().forEach(watchEvent -> {
                            Watchable watchable = watchKey.watchable();
                            //目录路径 （监听的注册目录）
                            Path dirPath = (Path) watchable;
                            // 事件所关联的对象即注册目录的子文件（或子目录）
                            // 事件发生源是相对路径
                            Path fileRelativePath = (Path) watchEvent.context();
                            if (resourceFileName.equals(fileRelativePath.getFileName().toString())){
                                //处理为绝对路径
                                Path filePath = dirPath.resolve(fileRelativePath);
                                File file = filePath.toFile();
                                try {
                                    Properties properties = loadMessageProperties(new FileReader(file));
                                    synchronized (messageProperties){
                                        messageProperties.clear();
                                        messageProperties.putAll(properties);
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } finally {
                    if (watchKey != null){
                        watchKey.reset(); // 重置
                    }
                }
            }
        });
    }

    private Properties loadMessageProperties() {
        EncodedResource encodedResource = new EncodedResource(this.messagesPropertiesResource,ENCODING);
        try {
            return loadMessageProperties(encodedResource.getReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Properties loadMessageProperties(Reader reader){
        Properties properties = new Properties();
        try{
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    private Resource getMessagePropertiesResource(){
        resourceLoader = getResourceLoader();
        return resourceLoader.getResource(resourcePath);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String messageFormatPattern = messageProperties.getProperty(code);
        if (StringUtils.hasText(messageFormatPattern)){
            return new MessageFormat(messageFormatPattern, locale);
        }
        return null;
    }


    public ResourceLoader getResourceLoader() {
        return resourceLoader == null? new DefaultResourceLoader() : resourceLoader;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        DynamicResourceMessageSource messageSource = new DynamicResourceMessageSource();
        IntStream.range(0,10000).forEach(
                (i) -> {
                    String message = messageSource.getMessage("name",new Object[]{},Locale.getDefault());
                    System.out.println(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
