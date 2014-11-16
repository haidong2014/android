package com.infodeliver.webservice.common.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SmsUtil {

    private static Logger logger = Logger.getLogger(SmsUtil.class);

    private SmsUtil() {
    }

    private static final String RESOURCE_SMS = "sms.properties";

    public static Element send(String phone, int captcha) throws Exception {
        Properties prop = ResourceUtil.reloadProperties(RESOURCE_SMS);

        String url = prop.getProperty("send.sms.url");
        logger.debug("********************send.sms.url" + url);
        String account = prop.getProperty("send.sms.account");
        logger.debug("********************send.sms.account" + account);
        String password = prop.getProperty("send.sms.password");
        logger.debug("********************send.sms.password" + password);

        PostMethod method = new PostMethod(url);
        HttpClient client = new HttpClient();
        String proxyFlag = prop.getProperty("send.sms.proxy.flag");
        logger.debug("********************send.sms.proxy.flag" + proxyFlag);
        if ("1".equals(proxyFlag)) {
            String proxyUrl = prop.getProperty("send.sms.proxy.url");
            logger.debug("********************send.sms.proxy.url" + proxyUrl);
            String proxyPort = prop.getProperty("send.sms.proxy.port");
            logger.debug("********************send.sms.proxy.port" + proxyPort);
            client.getHostConfiguration().setProxy(proxyUrl, Integer.parseInt(proxyPort));

            String proxyAuthFlag = prop.getProperty("send.sms.proxy.auth.flag");
            logger.debug("********************send.sms.proxy.auth.flag" + proxyAuthFlag);
            if ("1".equals(proxyAuthFlag)) {
                client.getParams().setAuthenticationPreemptive(true);
                String proxyUser = prop.getProperty("send.sms.proxy.user");
                logger.debug("********************send.sms.proxy.user" + proxyUser);
                String proxyPwd = prop.getProperty("send.sms.proxy.password");
                logger.debug("********************send.sms.proxy.password" + proxyPwd);
                client.getState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyUser, proxyPwd));
            }
        }

        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

        String content = MessageFormat.format(prop.getProperty("send.sms.content"), String.valueOf(captcha));
        logger.debug("********************send.sms.content" + content);

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", account),
                new NameValuePair("password", password), //密码可以使用明文密码或使用32位MD5加密
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };

        method.setRequestBody(data);

        client.executeMethod(method);

        String submitResult = method.getResponseBodyAsString();

        logger.debug("********************submitResult" + submitResult);

        Document doc = DocumentHelper.parseText(submitResult);
        return doc.getRootElement();
    }
}
