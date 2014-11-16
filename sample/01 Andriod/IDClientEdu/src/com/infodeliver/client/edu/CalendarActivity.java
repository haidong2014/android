package com.infodeliver.client.edu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.calendar.Adap_CaleInfo;
import com.infodeliver.client.edu.calendar.CaleListItem;
import com.infodeliver.client.edu.calendar.DisCaleIcons;
import com.infodeliver.client.ui.CalendarMoveView;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.client.utils.NSMsgFactory;
import com.infodeliver.io.NSHttpManager;
import com.infodeliver.utils.NSLog;

public class CalendarActivity extends NSActivityBase  {

    private CalendarMoveView m_calendar;	 				// 日历控件
    private ListView m_listDuxin;		 				// 通知List
    private Adap_CaleInfo m_adap_Duxin;					// 通知List的适配器
    private ArrayList<CaleListItem> m_listDuxinItem
                = new  ArrayList<CaleListItem> ();	// 通知List的项目一揽



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);


        // 通知情报一览进行设定
        init_duanxinlist();

        // 日历情报进行设定
        init_calendar();

        // 返回按钮等进行设定
        init_button();


    }

    // 日历显示月变更
    public void DisplayChangeMonth(String strYm)
    {
        // 显示月
        TextView txtMonth = (TextView)findViewById(R.id.calendarCenter);
        txtMonth.setText(strYm);
    }

    // 日历显示选择日
    public void ClickDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String strDate = sdf.format(date);

        // 通知情报一览的时间进行设定
        m_adap_Duxin.arrDuxin.get(0).strYmd = strDate;
        m_adap_Duxin.arrDuxin.get(1).strYmd = strDate;
        m_adap_Duxin.notifyDataSetChanged();


        // 根据选中的日期，从服务器取得当天的通知一览
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String strDateParame = sdf2.format(date);
        GetDayDuxinFromSever(strDateParame);


    }

    // 日历情报进行设定
    private void init_calendar()
    {
        // 获取日历控件对象
        m_calendar = (CalendarMoveView)findViewById(R.id.calendar);
        m_calendar.SetParent(this);

        // 显示月
        TextView txtMonth = (TextView)findViewById(R.id.calendarCenter);
        txtMonth.setText(m_calendar.m_calendar_middle.getYearAndmonth());

        GetNotIconsFromServer("2012/12/12");

    }


    // 通知情报一览进行设定
    private void init_duanxinlist()
    {
        m_listDuxin = (ListView)findViewById(R.id.listInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date  curDate = new Date(System.currentTimeMillis());
        String strDate = sdf.format(curDate);


        for(int i=1; i<=2; i++)
        {
            CaleListItem objItem = new CaleListItem();
            if (i ==1)
            {
                objItem.strTitle ="查看所有通知";
                objItem.strYmd = strDate;
                objItem.strNumber = "3";
                m_listDuxinItem.add(objItem);
            }else if (i ==2){
                objItem.strTitle ="查看所有日记";
                objItem.strYmd =  strDate;
                objItem.strNumber ="4";
                m_listDuxinItem.add(objItem);
            }
        }
        m_adap_Duxin = new Adap_CaleInfo(this,m_listDuxinItem);

        m_listDuxin.setAdapter(m_adap_Duxin);

        // 添加列表框项目点击事件
        m_listDuxin.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                m_adap_Duxin.setSelectedPosition(arg2);
                m_adap_Duxin.notifyDataSetInvalidated();
            }
        });
    }

    // 返回按钮等进行设定
    private void init_button()
    {
        Button bntRet  = (Button) findViewById(R.id.title_left_btn);
        bntRet.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        }
        );
    };

    // 根据选中的日期，取得当天的通知一览
    private void GetNotIconsFromServer(String strYmd)
    {
        try {
            String msg = NSMsgFactory.GetDayDuxin(strYmd);
            postMessage( NSBizConstant.REQUEST_GET_ICONS
                        ,NSBizConstant.SERVER_URL_GET_ICONS
                        , NSHttpManager.POST
                        , msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 根据选中的日期，取得当天的通知一览
    private void GetDayDuxinFromSever(String strYmd)
    {
        try {
            String msg = NSMsgFactory.GetDayDuxin(strYmd);
            postMessage( NSBizConstant.REQUEST_GET_DAY_DUANXIN
                        ,NSBizConstant.SERVER_URL_GET_DAY_DUANXIN
                        , NSHttpManager.POST
                        , msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        NSLog.e(errorCode);

    }


    @Override
    public void onResult(int requestID, String result)
    {
        NSLog.d("result json", result);

        String  strName  ="";
        switch (requestID) {

        // 以前的例子
        case NSBizConstant.REQUEST_QUERY_VINE_LIST:
            try {
                JSONObject vineObject = new JSONObject(result);
                JSONObject vineParam = vineObject.getJSONObject(NSBizConstant.KEY_PARAMS);
                JSONArray vineArray = vineParam.getJSONArray("list");
                for(int i=0;i<vineArray.length();i++)
                {
                      JSONObject item = (JSONObject)vineArray.opt(i);
                      strName = item.getString("USER_NAME");
                }

                int lxk  =123;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            break;


        // 取得通知所有的主题	图标
        case NSBizConstant.REQUEST_GET_ICONS:

            try {
                m_calendar.m_MapDayIcons.clear();
                JSONObject vineObject = new JSONObject(result);
                JSONObject vineParam = vineObject.getJSONObject(NSBizConstant.KEY_PARAMS);
                JSONArray vineArray = vineParam.getJSONArray("list");

                DisCaleIcons bojDisInfo = new DisCaleIcons();

                // vineArray.length() =0 是取得的数据为空
                for(int i=0;i<vineArray.length();i++)
                {
                      JSONObject item = (JSONObject)vineArray.opt(i);
                      String strId = item.getString("ID");
                      String strSendDate = item.getString("DATE1");
                      String strIcon = item.getString("CATEGORY_ID");
                      if (m_calendar.m_MapDayIcons.containsKey(strSendDate))
                      {
                          DisCaleIcons objDisInfo = m_calendar.m_MapDayIcons.get(strSendDate);
                          objDisInfo.PutIcons(strIcon);
                      }else {
                          DisCaleIcons objDisInfo =  new DisCaleIcons();
                          objDisInfo.m_strDate = strSendDate;
                          objDisInfo.PutIcons(strIcon);
                          m_calendar.m_MapDayIcons.put(objDisInfo.m_strDate, objDisInfo);
                      }
                }
                m_calendar.m_calendar_middle.invalidate();
                m_calendar.m_calendar_Header.invalidate();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;

        // 取得选择日的通知
        case NSBizConstant.REQUEST_GET_DAY_DUANXIN:

                try {
                    JSONObject vineObject = new JSONObject(result);
                    JSONObject vineParam = vineObject.getJSONObject(NSBizConstant.KEY_PARAMS);
                    JSONArray vineArray = vineParam.getJSONArray("list");

                    DisCaleIcons bojDisInfo = new DisCaleIcons();

                    // vineArray.length() =0 是取得的数据为空
                    for(int i=0;i<vineArray.length();i++)
                    {
                          JSONObject item = (JSONObject)vineArray.opt(i);
                          String strId = item.getString("ID");
                          String strSendDate = item.getString("DATE1");
                          String strIcon = item.getString("CATEGORY_ID");
                          /*
                          if (m_calendar.m_MapDayIcons.containsKey(strSendDate))
                          {
                              DisCaleIcons objDisInfo = m_calendar.m_MapDayIcons.get(strSendDate);
                              objDisInfo.PutIcons(strIcon);
                          }else {
                              DisCaleIcons objDisInfo =  new DisCaleIcons();
                              objDisInfo.m_strDate = strSendDate;
                              objDisInfo.PutIcons(strIcon);
                              m_calendar.m_MapDayIcons.put(objDisInfo.m_strDate, objDisInfo);
                          }*/
                    }

                    //m_calendar.m_calendar_middle.invalidate();
                    //m_calendar.m_calendar_Header.invalidate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;



        default:
            break;
        }
    }




}

