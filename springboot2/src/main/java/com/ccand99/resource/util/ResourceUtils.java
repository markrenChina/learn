package com.ccand99.resource.util;


import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * {@link Resource} 工具类
 */
public interface ResourceUtils {

    static String getContent(Resource resource){
        var encodeResource = new EncodedResource(resource,"UTF-8");
        try(Reader reader = encodeResource.getReader()) {
            return IOUtils.toString(reader);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static String getContent(Resource resource, String encoding) throws IOException {
        var encodeResource = new EncodedResource(resource,encoding);
        try(Reader reader = encodeResource.getReader()) {
            return IOUtils.toString(reader);
        }
    }
}
