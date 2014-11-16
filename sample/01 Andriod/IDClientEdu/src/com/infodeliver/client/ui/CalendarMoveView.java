package com.infodeliver.client.ui;


import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.infodeliver.client.edu.CalendarActivity;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.edu.calendar.DisCaleIcons;

//@SuppressLint("NewApi") public class CalendarMoveView extends FrameLayout  implements View.OnTouchListener
@SuppressLint("NewApi") public class CalendarMoveView extends FrameLayout
{
    public LinearLayout m_layout_header;
    public LinearLayout m_layout_middle;
    public LinearLayout m_layout_foot;

    public CalendarView m_calendar_Header;
    public CalendarView m_calendar_middle;
    public CalendarView m_calendar_footer;


    private int m_nChangeCount = 0;
    private CalendarActivity m_parentAcvity;
    public  boolean m_bIsMove = false;

    public HashMap<String,DisCaleIcons> m_MapDayIcons = new HashMap<String,DisCaleIcons>();

    public void SetParent (CalendarActivity argActivity)
    {
        m_parentAcvity = argActivity;
        SetClickCalendar();
    }
    public void SetClickCalendar()
    {
        //m_calendar_middle.setOnChangeMonthListener(new OnChangeMonthListener()
        m_calendar_middle.setOnChangeMonthListener(new  CalendarView.OnChangeMonthListener()
        {
            @Override
            public void OnChangeMonth(String strYm)
            {
                m_parentAcvity.DisplayChangeMonth(strYm);
            }
        });

        m_calendar_middle.setOnItemClickListener(new CalendarView.OnItemClickListener()
        {
            @Override
            public void OnItemClick(Date date)
            {
                m_parentAcvity.ClickDate(date);
            }
        });

    }

    public void ChangeView()
    {
        m_calendar_Header.setOnKeyListener(null);
        m_calendar_Header.setOnItemClickListener(null);

        m_calendar_middle.setOnKeyListener(null);
        m_calendar_middle.setOnItemClickListener(null);

        if (m_nChangeCount ==0 )
        {

            m_layout_middle   = (LinearLayout) findViewById(R.id.layout_move_header);
            m_calendar_middle = (CalendarView)findViewById(R.id.calendar_header);

            m_layout_header = (LinearLayout) findViewById(R.id.layout_move_middle);
            m_calendar_Header = (CalendarView)findViewById(R.id.calendar_normal);
            m_nChangeCount = 1;

        }else if (m_nChangeCount ==1)
        {
            m_layout_header  = (LinearLayout) findViewById(R.id.layout_move_header);
            m_layout_middle = (LinearLayout) findViewById(R.id.layout_move_middle);

            m_calendar_Header = (CalendarView)findViewById(R.id.calendar_header);
            m_calendar_middle = (CalendarView)findViewById(R.id.calendar_normal);

            m_nChangeCount = 0;
        }
        SetClickCalendar();
    }
    public CalendarMoveView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_move,this);


        //
        m_layout_header  = (LinearLayout) findViewById(R.id.layout_move_header);
        m_layout_middle = (LinearLayout) findViewById(R.id.layout_move_middle);
        m_layout_foot = (LinearLayout) findViewById(R.id.layout_move_foot);



        //
        m_calendar_Header = (CalendarView)findViewById(R.id.calendar_header);
        m_calendar_middle = (CalendarView)findViewById(R.id.calendar_normal);
        m_calendar_footer = (CalendarView)findViewById(R.id.calendar_foot);

        m_nChangeCount = 0;

        init();

        /*
        listener = new FlingCalendarMove(context,this);
        detector = new GestureDetector(context,listener);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
        setOnTouchListener(this);
        */



    }

    public CalendarMoveView(Context context)
    {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_move,this);


        //
        m_layout_header  = (LinearLayout) findViewById(R.id.layout_move_header);
        m_layout_middle = (LinearLayout) findViewById(R.id.layout_move_middle);
        m_layout_foot = (LinearLayout) findViewById(R.id.layout_move_foot);



        //
        m_calendar_Header = (CalendarView)findViewById(R.id.calendar_header);
        m_calendar_middle = (CalendarView)findViewById(R.id.calendar_normal);
        m_calendar_footer = (CalendarView)findViewById(R.id.calendar_foot);

        init();


        /*
        listener = new FlingCalendarMove(context,this);
        detector = new GestureDetector(context,listener);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);
        setOnTouchListener(this);
        */

    }

    public void SetBeginMovePos(int nargDownUp)
    {
        float fHeight = (float)(m_calendar_middle.surface.height);
        // down
        if (nargDownUp == 0)
        {
            setTop(m_layout_header,-fHeight);
            //m_calendar_Header.clickLeftMonth();
        // upd
        }else if (nargDownUp == 1)
        {
            setTop(m_layout_header,fHeight);
            //m_calendar_Header.clickRightMonth();
        }
    }

    public void init_CalendarPos()
    {
        float fHeight = (float)(m_calendar_middle.surface.height);
        //fHeight =768;
        
        setTop(m_layout_middle,0);
        setTop(m_layout_header,-fHeight);
        setTop(m_layout_foot,fHeight);
        
        /*
        m_layout_middle.setY(0);
        m_layout_header.setY(-fHeight);
        m_layout_foot.setY(fHeight);
        */

        float posheader1 = m_layout_header.getTop();
        float posmiddle = m_layout_middle.getTop();
        float posfoot  = m_layout_foot.getTop();

    }


    private void init()
    {
        float fHeight = 10000;
        setTop(m_layout_header,-fHeight);
        setTop(m_layout_foot,fHeight);
        
        /*
        m_layout_header. (-fHeight);
        m_layout_foot.setY(fHeight);
        */

        //m_calendar_Header.clickLeftMonth();

        m_calendar_Header.SetParent(this);
        m_calendar_middle.SetParent(this);
        //m_calendar_footer.SetParent(this);

        //m_calendar_middle.GetICons();

    }

    /*
    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        // TODO Auto-generated method stub
        return detector.onTouchEvent(arg1);
    }*/

    public void moveTop(View argView, float argYpos)
    {
    	  int nLeft = m_layout_header.getLeft();
          int nTop = m_layout_header.getTop() + (int)argYpos;
           
          int nRight = nLeft + m_layout_header.getWidth();
          int nBottom = nTop + m_layout_header.getBottom();
          argView.layout(nLeft, nTop, nRight, nBottom);
    }
    
    public void setTop(View argView, float argYpos)
    {
    	  int nLeft = m_layout_header.getLeft();
          int nTop = (int)argYpos;
           
          int nRight = nLeft + m_layout_header.getWidth();
          int nBottom = nTop + m_layout_header.getBottom();
          argView.layout(nLeft, nTop, nRight, nBottom);
    }
    
    public void SetYPos(float argYpos)
    {
    	
    	moveTop(m_layout_header,argYpos);
    	moveTop(m_layout_middle,argYpos);
    }

        //nMovePos = m_layout_foot.getY() + argYpos;
             //m_layout_foot.setY(nMovePos);

}



