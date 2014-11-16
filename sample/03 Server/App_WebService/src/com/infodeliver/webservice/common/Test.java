package com.infodeliver.webservice.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        countdown();
//        Test t = new Test();
//        t.countdown(10);
//        t.startCount();
//        System.out.println("倒计时开始！");
        System.out.println(StringUtils.defaultString(null));
    }

    private static void countdown(int seconds) {
        System.err.println("倒计时" + seconds + "秒,倒计时开始:");
          int i = seconds;
          while (i > 0) {
           System.err.println(i);
           try {
            Thread.sleep(1000);
           } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           }
           i--;
          }
          System.err.println(i);
          System.err.println("倒计时结束");
    }

    int count = 30;
    private TimerTask timerTask;
    private Timer timer;

    public void startCount() {
        timer = new Timer();
        timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (count > 0) {System.out.println(""+count);
                    }
                    else {
                        System.out.println("重新获取");
                        timerTask.cancel();
                    }
                    count --;
                }
        };
        timer.schedule(timerTask, 0, 1000);
    }

}
