package com.infodeliver.webservice.common.conf;

import org.apache.log4j.Logger;

public final class DBResource {

    private static Logger logger = Logger.getLogger(DBResource.class);

    private static DBResource m_instance = new DBResource();

    private DBResource() {}

    public static DBResource getInstance() {
        return m_instance;
    }

    public static void refresh() {
//		if (m_instance.m_props != null) {
//			synchronized (m_instance.m_props) {
//				logger.trace(5, "refresh()", null, "SGファイルの再読み込みを開始します。", null, null, null);
//				m_instance.m_props = WRCCZJ_ResourceUtil.reloadProperties(m_resourcePath);
//				logger.trace(5, "refresh()", null, "SGファイルの再読み込みを終了します。", null, null, null);
//			}
//		} else {
//			logger.trace(5, "refresh()", null, "SGファイルの初期読み込みを開始します。", null, null, null);
//			m_instance.m_props = WRCCZJ_ResourceUtil.reloadProperties(m_resourcePath);
//			logger.trace(5, "refresh()", null, "SGファイルの初期読み込みを終了します。", null, null, null);
//		}
    }

    public static void init(String path) {
//		m_resourcePath = path;
        refresh();
    }

    public static void startObservation() {
        Thread thread = new DBObserverThread();
        thread.setName(thread.getClass().getName());
        thread.setDaemon(true);
        thread.start();
    }

    private static class DBObserverThread extends Thread {
        public void run() {
            try {
                while (true) {
                    Thread.sleep(100 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
