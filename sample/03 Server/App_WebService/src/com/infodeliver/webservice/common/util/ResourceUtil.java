package com.infodeliver.webservice.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class ResourceUtil {

    private static Logger logger = Logger.getLogger(ResourceUtil.class);

    public static Properties reloadProperties(String resource) {
        Properties properties = new Properties();
        try {
            String path = ResourceUtil.class.getClassLoader().getResource(resource).getPath();
            URI uri = new URI(path.toString());
            InputStream is = new FileInputStream(uri.getPath());
            properties.load(is);
        }catch (Exception e) {
            logger.error("配置文件不正确，请确认配置文件" + resource);
        }
        return properties;
    }
}
