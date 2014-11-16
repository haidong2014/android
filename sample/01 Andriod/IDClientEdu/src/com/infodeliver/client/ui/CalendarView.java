package com.infodeliver.client.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.infodeliver.client.edu.R;
import com.infodeliver.client.edu.calendar.CaledarMoveTask;
import com.infodeliver.client.edu.calendar.DisCaleIcons;

public class CalendarView extends View implements View.OnTouchListener
{
    private final static String TAG = "anCalendar";
    public Date selectedStartDate;
    public Date selectedEndDate;
    public Date downDate; 							// 手指按下状态时临时日期

    private Date curDate; 							// 当前日历显示的月
    private Date today; 							// 今天的日期文字显示红色

    private Date showFirstDate, showLastDate; 		// 日历显示的第一个日期和最后一个日期
    private int downIndex; 							// 按下的格子索引

    private Calendar calendar;						// 日历
    public Surface surface;  						// 显示风格

    private int[] date = new int[42]; 				// 日历显示数字
    private Date[] dateYmd = new Date[42];			// 日历显示数字(日期类型)
    private int curStartIndex, curEndIndex; 		// 当前显示的日历(date)起始的索引

    private GestureDetector detector;				// 手势监听
    private FlingCalendar listener;					// 手势监听
    private Matrix matrix_downIndex = new Matrix();	//坐标矩阵
    private CalendarMoveView m_MoveViewParent;


    //给控件设置监听事件
    public OnItemClickListener onItemClickListener; 	//日选择
    public OnChangeMonthListener onChangeMonthListener;	//月选择


    public void SetParent(CalendarMoveView argMoveViewParent)
    {
        m_MoveViewParent = argMoveViewParent;
        listener.SetParent(argMoveViewParent);
    }
    public CalendarView(Context context)
    {
        super(context);

        // 初始化日期
        init();
        // 初始化手势
        init_gesture(context);
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        // 初始化日期
        init();
        // 初始化手势
        init_gesture(context);
    }



    // 初始化日期
    private void init()
    {
        curDate = selectedStartDate = selectedEndDate = today = new Date(System.currentTimeMillis());
        calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        surface = new Surface();
        surface.density = getResources().getDisplayMetrics().density;
        setBackgroundColor(surface.bgColor);
        setOnTouchListener(this);
        
        // 计算日期
        calculateDate();
    }

    // 初始化手势
    private void init_gesture(Context context)
    {
        listener = new FlingCalendar(context,this);
        detector = new GestureDetector(context,listener);
        setFocusable(true);
        setClickable(true);
        setLongClickable(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        surface.width = getResources().getDisplayMetrics().widthPixels;
        surface.height = (int) (getResources().getDisplayMetrics().heightPixels*3/5);
//      if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.EXACTLY) {
//          surface.width = View.MeasureSpec.getSize(widthMeasureSpec);
//      }
//      if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.EXACTLY) {
//          surface.height = View.MeasureSpec.getSize(heightMeasureSpec);
//      }
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
                View.MeasureSpec.EXACTLY);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height,
                View.MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        Log.d(TAG, "[onLayout] changed:"
                + (changed ? "new size" : "not change") + " left:" + left
                + " top:" + top + " right:" + right + " bottom:" + bottom);
        if (changed) {
            surface.init();
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public void DrawWeekImage(Canvas canvas
                                ,int index
                                ,float weekTextX
                                ,float weekTextY)
    {
        Bitmap bm = null;
        if (index ==0)
        {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_00);
        }else if (index ==1)
        {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_01);
        }else if (index ==2){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_02);
        }else if (index ==3){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_03);
        }else if (index == 4){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_04);
        }else if (index ==5){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_05);
        }else if (index ==6){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_06);
        }

        Bitmap bmUpdate = Bitmap.createScaledBitmap(bm
                                    , (int)surface.cellWidth
                                    ,(int)surface.weekHeight, true);


        canvas.drawBitmap(bmUpdate, weekTextX,weekTextY
                        , surface.weekPaint);


    }
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        

        // 按下状态，选择状态背景色
        drawDownOrSelectedBg(canvas);
        // write date number
        // today index
        int todayIndex = -1;
        calendar.setTime(curDate);
        String curYearAndMonth = calendar.get(Calendar.YEAR) + ""
                + calendar.get(Calendar.MONTH);
        calendar.setTime(today);
        String todayYearAndMonth = calendar.get(Calendar.YEAR) + ""
                + calendar.get(Calendar.MONTH);
        if (curYearAndMonth.equals(todayYearAndMonth)) {
            int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
            todayIndex = curStartIndex + todayNumber - 1;
        }
        for (int i = 0; i < 42; i++) {
            String strYm= GetAddMonth(0);
            int color = surface.textColor;
            if (isLastMonth(i)) {
                color = surface.borderColor;
                strYm = GetAddMonth(-1);
            } else if (isNextMonth(i)) {
                color = surface.borderColor;
                strYm = GetAddMonth(1);
            }
            if (todayIndex != -1 && i == todayIndex) {
                color = surface.todayNumberColor;
            }
            try {
                drawCellText(canvas, i, date[i] + "", color
                            ,strYm);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.onDraw(canvas);
    }

    private void calculateDate() {
        calendar.setTime(curDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d(TAG, "day in week:" + dayInWeek);
        int monthStart = dayInWeek;
        if (monthStart == 1) {
            monthStart = 8;
        }
        monthStart -= 1;  //以日为开头-1，以星期一为开头-2
        curStartIndex = monthStart;
        date[monthStart] = 1;
        dateYmd[monthStart] = calendar.getTime();

        // last month
        if (monthStart > 0) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = monthStart - 1; i >= 0; i--) {
                date[i] = dayInmonth;
                calendar.set(Calendar.DAY_OF_MONTH, date[i]);
                dateYmd[i] = calendar.getTime();
                dayInmonth--;
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[0]);
        }
        showFirstDate = calendar.getTime();
        dateYmd[0] = showFirstDate;
        // this month
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        // Log.d(TAG, "m:" + calendar.get(Calendar.MONTH) + " d:" +
        // calendar.get(Calendar.DAY_OF_MONTH));
        int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < monthDay; i++) {
            date[monthStart + i] = i + 1;
            calendar.set(Calendar.DAY_OF_MONTH, date[monthStart + i]);
            dateYmd[monthStart + i] = calendar.getTime();

        }
        curEndIndex = monthStart + monthDay;
        if (curEndIndex < 42) {
            // 显示了下一月的
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // next month
        for (int i = monthStart + monthDay; i < 42; i++) {
            date[i] = i - (monthStart + monthDay) + 1;
            calendar.set(Calendar.DAY_OF_MONTH, date[i]);
            dateYmd[i] = calendar.getTime();
        }

        calendar.set(Calendar.DAY_OF_MONTH, date[41]);
        showLastDate = calendar.getTime();
        dateYmd[41] = showLastDate;
    }

    private Bitmap GetBitRes(int index)
    {
        Bitmap bm = null;
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_bas);
        /*
            if (index ==0)
            {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face1);
            }else if (index ==1)
            {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face2);
            }else if (index ==2){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face3);
            }else if (index ==3){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face4);
            }else if (index == 4){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face5);
            }else if (index ==5){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face6);
            }else if (index ==6){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face7);
            }else if (index == 7){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face8);
            }else if (index ==8){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face9);
            }else if (index ==9){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_face10);
            }else {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img01);
            }
        */
        return bm;
    }

    private Bitmap GetNotifyIcon(int index)
    {
        Bitmap bm = null;
            if (index ==1)
            {
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_day_notify01);
            }else if (index ==2){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_day_notify02);
            }else if (index ==3){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_day_notify03);
            }else if (index == 4){
                bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_day_notify04);
            }
        return bm;
    }

    private Point GetUpdImageSize(Bitmap argBm,int nargMaxWith,int nargMaxHeight)
    {
        Point pRet = new Point(nargMaxWith,nargMaxHeight);
        float nWith = argBm.getWidth();
        float nHeight = argBm.getHeight();

        float nWHRate = nWith /nHeight;
        if (nWHRate*nargMaxHeight<=nargMaxWith)
        {
            pRet.x = (int)(nWHRate*nargMaxHeight);
        }else {
            pRet.y = (int)(nargMaxWith/nWHRate);
        }
        return pRet;

    }
    private void drawCell_Header(Canvas canvas
                                , int index
                                , float cellXTop
                                , float cellYTop
                                )
    {
        Bitmap bm = null;
        if (index ==1)
        {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_00);
        }else if (index ==2)
        {
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_01);
        }else if (index ==3){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_02);
        }else if (index ==4){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_03);
        }else if (index == 5){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_04);
        }else if (index ==6){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_05);
        }else if (index ==7){
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.cell_06);
        }

        Bitmap bmUpdate = Bitmap.createScaledBitmap(bm
                                    , (int)surface.cellWidth
                                    ,(int)surface.cellHeight_Header, true);


        canvas.drawBitmap(bmUpdate, cellXTop,cellYTop
                        , surface.weekPaint);

    }
    /**
     *
     * @param canvas
     * @param index
     * @param text
     * @throws ParseException
     */
    private void drawCellText(Canvas canvas, int index, String text, int color,String argYMonth) throws ParseException {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.datePaint.setColor(color);
        float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight + surface.cellHeight * 3 / 4f;
        float cellX = (surface.cellWidth * (x - 1))
                + (surface.cellWidth - surface.datePaint.measureText(text))
                / 2f;

        float cellXTop= (surface.cellWidth * (x - 1));
        float cellYTop= surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight ;

        // 日期进行描绘
        drawCell_Header(canvas,x,cellXTop,cellYTop);
        canvas.drawText(text, cellX
                , cellYTop+surface.datePaint.getTextSize()
                , surface.datePaint);



        // 日期下面的框进行描画
        float ImgRectX = cellXTop;
        float ImgRectY = cellYTop+surface.datePaint.getTextSize();
        float ImgRectW= surface.cellWidth;
        float ImgRectH= surface.cellHeight - surface.datePaint.getTextSize();
        Bitmap bm = GetBitRes (index);

        Paint paint = new Paint();
        Point pUpdImgSzie = GetUpdImageSize(bm
                ,(int)ImgRectW
                ,(int)ImgRectH);

        Bitmap bmUpdate = Bitmap.createScaledBitmap(bm
                        , (int)(pUpdImgSzie.x)
                        , (int)(pUpdImgSzie.y)
                        ,true);
        Matrix matrix = new Matrix();
        matrix.postTranslate(ImgRectX+(ImgRectW -pUpdImgSzie.x )/2
                            , ImgRectY+(ImgRectH -pUpdImgSzie.y)/2);
        canvas.drawBitmap(bmUpdate, matrix
                          , paint);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String strCurrDate = sdf2.format(dateYmd[index]);

        if (m_MoveViewParent== null)
        {
            return;
        }
        if (m_MoveViewParent.m_MapDayIcons.isEmpty())
        {
            return;
        }

        if (m_MoveViewParent.m_MapDayIcons.containsKey(strCurrDate))
        {
            DisCaleIcons objIcon = m_MoveViewParent.m_MapDayIcons.get(strCurrDate);

            drawCell_oneNotify(canvas
                        , Integer.parseInt(objIcon.m_nIconId_1)
                        ,ImgRectX
                        ,ImgRectY);
            drawCell_oneNotify(canvas
                        , Integer.parseInt(objIcon.m_nIconId_2)
                        ,ImgRectX+surface.cellWidth/2
                        ,ImgRectY);

            drawCell_oneNotify(canvas
                        , Integer.parseInt(objIcon.m_nIconId_3)
                        ,ImgRectX
                        ,ImgRectY+surface.cellHeight/2-12);

            drawCell_oneNotify(canvas
                    , Integer.parseInt(objIcon.m_nIconId_4)
                        ,ImgRectX+surface.cellWidth/2
                        ,ImgRectY+surface.cellHeight/2-12);
        }
    }

    private void drawCell_oneNotify(Canvas canvas
                                    , int index
                                    ,float argImgRectX
                                    ,float argImgRectY
                                    ) throws ParseException
    {
        if (index <=0)
        {
            return ;
        }

        // 日期下面的框进行描画
        float ImgRectX = argImgRectX;
        float ImgRectY = argImgRectY;
        float ImgRectW= surface.cellWidth/2;
        float ImgRectH=( surface.cellHeight - surface.datePaint.getTextSize())/2;
        Bitmap bm = GetNotifyIcon(index);

        Paint paint = new Paint();
        Point pUpdImgSzie = GetUpdImageSize(bm
                ,(int)ImgRectW
                ,(int)ImgRectH);

        Bitmap bmUpdate = Bitmap.createScaledBitmap(bm
                        , (int)(pUpdImgSzie.x)
                        , (int)(pUpdImgSzie.y)
                        ,true);
        Matrix matrix = new Matrix();
        matrix.postTranslate(ImgRectX+(ImgRectW -pUpdImgSzie.x )/2
                            , ImgRectY+(ImgRectH -pUpdImgSzie.y)/2);
        canvas.drawBitmap(bmUpdate, matrix
                          , paint);
    }

    /**
     *
     * @param canvas
     * @param index
     * @param color
     */
    private void drawCellBg(Canvas canvas, int index, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.cellBgPaint.setColor(color);
        float left = surface.cellWidth * (x - 1) + surface.borderWidth;
        float top = surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight + surface.borderWidth;
        canvas.drawRect(left, top, left + surface.cellWidth
                - surface.borderWidth, top + surface.cellHeight
                - surface.borderWidth, surface.cellBgPaint);
    }

    private void drawDownOrSelectedBg(Canvas canvas) {
        // down and not up
        if (downDate != null) {
            drawCellBg(canvas, downIndex, surface.cellDownColor);
        }
        // selected bg color
        if (!selectedEndDate.before(showFirstDate)
                && !selectedStartDate.after(showLastDate)) {
            int[] section = new int[] { -1, -1 };
            calendar.setTime(curDate);
            calendar.add(Calendar.MONTH, -1);
            findSelectedIndex(0, curStartIndex, calendar, section);
            if (section[1] == -1) {
                calendar.setTime(curDate);
                findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
            }
            if (section[1] == -1) {
                calendar.setTime(curDate);
                calendar.add(Calendar.MONTH, 1);
                findSelectedIndex(curEndIndex, 42, calendar, section);
            }
            if (section[0] == -1) {
                section[0] = 0;
            }
            if (section[1] == -1) {
                section[1] = 41;
            }
            for (int i = section[0]; i <= section[1]; i++) {
                drawCellBg(canvas, i, surface.cellSelectedColor);
            }
        }
    }

    private void findSelectedIndex(int startIndex, int endIndex,
            Calendar calendar, int[] section) {
        for (int i = startIndex; i < endIndex; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, date[i]);
            Date temp = calendar.getTime();
            // Log.d(TAG, "temp:" + temp.toLocaleString());
            if (temp.compareTo(selectedStartDate) == 0) {
                section[0] = i;
            }
            if (temp.compareTo(selectedEndDate) == 0) {
                section[1] = i;
                return;
            }
        }
    }

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    public Date getSelectedEndDate() {
        return selectedEndDate;
    }

    private boolean isLastMonth(int i) {
        if (i < curStartIndex) {
            return true;
        }
        return false;
    }

    private boolean isNextMonth(int i) {
        if (i >= curEndIndex) {
            return true;
        }
        return false;
    }

    private int getXByIndex(int i) {
        return i % 7 + 1; // 1 2 3 4 5 6 7
    }

    private int getYByIndex(int i) {
        return i / 7 + 1; // 1 2 3 4 5 6
    }

    // 获得当前应该显示的年月
    public String getYearAndmonth() {
        calendar.setTime(curDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return year + "-" + surface.monthText[month];
    }

    //上一月
    public String GetAddMonth(int argAddMonth)
    {
        String strReturn = "";

        Calendar objCalendar = Calendar.getInstance();
        objCalendar.setTime(curDate);

        objCalendar.add(Calendar.MONTH, argAddMonth);
        int year = objCalendar.get(Calendar.YEAR);
        int month = objCalendar.get(Calendar.MONTH);
        strReturn =year + "年" + surface.monthText[month] ;
        return strReturn;

        //return year + "年" + surface.monthText[month]＋"月" ;
        /*
        Calendar objCalendar = Calendar.getInstance();
        objCalendar.setTime(curDate);
        objCalendar.add(Calendar.MONTH, argAddMonth);

        int year = objCalendar.get(Calendar.YEAR);
        int month = objCalendar.get(Calendar.MONTH);
        return year + "年" + surface.monthText[month]＋"月" ;
        */
    }


    //上一月
    public String clickLeftMonth(){
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, -1);
        curDate = calendar.getTime();
        calculateDate();
        invalidate();
        String strReturn = getYearAndmonth();
        if (onChangeMonthListener!=null)
        {
            onChangeMonthListener.OnChangeMonth(strReturn);
        }
        return strReturn;
    }


    //下一月
    public String clickRightMonth(){
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        curDate = calendar.getTime();
        calculateDate();
        invalidate();
        String strReturn = getYearAndmonth();
        if (onChangeMonthListener!=null)
        {
            onChangeMonthListener.OnChangeMonth(strReturn);
        }
        return strReturn;
    }



    //上一月
    public String clickLeftMonth_WithoutInvalid(){
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, -1);
        curDate = calendar.getTime();
        String strReturn = getYearAndmonth();
        if (onChangeMonthListener!=null)
        {
            onChangeMonthListener.OnChangeMonth(strReturn);
        }
        return strReturn;
    }


    //下一月
    public String clickRightMonth_WithoutInvalid(){
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        curDate = calendar.getTime();

        String strReturn = getYearAndmonth();
        if (onChangeMonthListener!=null)
        {
            onChangeMonthListener.OnChangeMonth(strReturn);
        }
        return strReturn;
    }

    public void setSelectedDateByCoor(float x, float y) {
        // change month
//      if (y < surface.monthHeight) {
//          // pre month
//          if (x < surface.monthChangeWidth) {
//              calendar.setTime(curDate);
//              calendar.add(Calendar.MONTH, -1);
//              curDate = calendar.getTime();
//          }
//          // next month
//          else if (x > surface.width - surface.monthChangeWidth) {
//              calendar.setTime(curDate);
//              calendar.add(Calendar.MONTH, 1);
//              curDate = calendar.getTime();
//          }
//      }
        // cell click down
        if (y > surface.monthHeight + surface.weekHeight) {
            int m = (int) (Math.floor(x / surface.cellWidth) + 1);
            int n = (int) (Math
                    .floor((y - (surface.monthHeight + surface.weekHeight))
                            / Float.valueOf(surface.cellHeight)) + 1);
            downIndex = (n - 1) * 7 + m - 1;
            //nRotate = 0;
            Log.d(TAG, "downIndex:" + downIndex);
            calendar.setTime(curDate);
            if (isLastMonth(downIndex)) {
                calendar.add(Calendar.MONTH, -1);
            } else if (isNextMonth(downIndex)) {
                calendar.add(Calendar.MONTH, 1);
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
            //downDate = calendar.getTime();
            downDate = dateYmd[downIndex];
        }
        invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
        /*
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            setSelectedDateByCoor(event.getX(), event.getY());
            break;
        case MotionEvent.ACTION_UP:
            if (downDate != null) {
//              if (!completed) {
//                  if (downDate.before(selectedStartDate)) {
//                      selectedEndDate = selectedStartDate;
//                      selectedStartDate = downDate;
//                  } else {
//                      selectedEndDate = downDate;
//                  }
//                  completed = true;
//              } else {
//                  selectedStartDate = selectedEndDate = downDate;
//                  completed = false;
//              }
                selectedStartDate = selectedEndDate = downDate;
                //响应监听事件
                onItemClickListener.OnItemClick(selectedStartDate);
                // Log.d(TAG, "downdate:" + downDate.toLocaleString());
                //Log.d(TAG, "start:" + selectedStartDate.toLocaleString());
                //Log.d(TAG, "end:" + selectedEndDate.toLocaleString());
                //downDate = null;
                invalidate();
            }
            break;
        }
        return true;
        */
    }

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener =  onItemClickListener;
    }
    //给控件设置监听事件
    public void setOnChangeMonthListener(OnChangeMonthListener onChangeMonthListener){
        this.onChangeMonthListener =  onChangeMonthListener;
    }

    //监听接口
    public interface OnItemClickListener {
        void OnItemClick(Date date);
    }

    public interface OnChangeMonthListener
    {
          public void OnChangeMonth(String strYm);
    }

    /**
     *
     * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
     */
    public class Surface {
        public float density;
        public int width; // 整个控件的宽度
        public int height; // 整个控件的高度
        public float monthHeight; // 显示月的高度
        //public float monthChangeWidth; // 上一月、下一月按钮宽度
        public float weekHeight; // 显示星期的高度
        public float cellWidth; // 日期方框宽度
        public float cellHeight; // 日期方框高度
        public float cellHeight_Header;
        public float cellHeight_Image;

        public float borderWidth;
        public int bgColor = Color.parseColor("#FFFFFF");
        private int textColor = Color.BLACK;
        //private int textColorUnimportant = Color.parseColor("#666666");
        private int btnColor = Color.parseColor("#666666");
        private int borderColor = Color.parseColor("#CCCCCC");
        public int todayNumberColor = Color.RED;
        public int cellDownColor = Color.parseColor("#CCFFFF");
        public int cellSelectedColor = Color.parseColor("#99CCFF");
        public Paint borderPaint;
        public Paint monthPaint;
        public Paint weekPaint;
        public Paint datePaint;
        public Paint monthChangeBtnPaint;
        public Paint cellBgPaint;
        public Path boxPath; // 边框路径
        //public Path preMonthBtnPath; // 上一月按钮三角形
        //public Path nextMonthBtnPath; // 下一月按钮三角形
        public String[] weekText = {"日","一","二", "三", "四", "五", "六"};
        public String[] monthText = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};

        public void init() {
            float temp = height / 7f;
            monthHeight = 0;//(float) ((temp + temp * 0.3f) * 0.6);
            //monthChangeWidth = monthHeight * 1.5f;
            //weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
            weekHeight = (float) (height/15);
            // lixiaokui add
            weekHeight=0;
            cellHeight = (height - monthHeight - weekHeight) / 6f;

            cellHeight_Header = cellHeight/4;
            cellHeight_Image = cellHeight - cellHeight_Header;

            cellWidth = width / 7f;
            borderPaint = new Paint();
            borderPaint.setColor(borderColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderWidth = (float) (0.5 * density);
            // Log.d(TAG, "borderwidth:" + borderWidth);
            borderWidth = borderWidth < 1 ? 1 : borderWidth;
            borderPaint.setStrokeWidth(borderWidth);
            monthPaint = new Paint();
            monthPaint.setColor(textColor);
            monthPaint.setAntiAlias(true);
            float textSize = cellHeight * 0.4f;
            Log.d(TAG, "text size:" + textSize);
            monthPaint.setTextSize(textSize);
            monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
            weekPaint = new Paint();
            weekPaint.setColor(textColor);
            weekPaint.setAntiAlias(true);
            //float weekTextSize = weekHeight * 0.6f;
            float weekTextSize = weekHeight * 0.4f;
            weekPaint.setTextSize(weekTextSize);
            weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
            weekPaint.setTextAlign(Align.LEFT);
            datePaint = new Paint();
            datePaint.setColor(textColor);
            datePaint.setAntiAlias(true);
            //float cellTextSize = cellHeight * 0.5f;
            float cellTextSize = cellHeight * 0.2f;
            datePaint.setTextSize(cellTextSize);
            //datePaint.setTypeface(Typeface.DEFAULT_BOLD);
            boxPath = new Path();
            //boxPath.addRect(0, 0, width, height, Direction.CW);
            //boxPath.moveTo(0, monthHeight);
            boxPath.rLineTo(width, 0);
            boxPath.moveTo(0, monthHeight + weekHeight);
            boxPath.rLineTo(width, 0);
            for (int i = 1; i < 6; i++) {
                boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
                boxPath.rLineTo(width, 0);
                boxPath.moveTo(i * cellWidth, monthHeight);
                boxPath.rLineTo(0, height - monthHeight);
            }
            boxPath.moveTo(6 * cellWidth, monthHeight);
            boxPath.rLineTo(0, height - monthHeight);
            //preMonthBtnPath = new Path();
            //int btnHeight = (int) (monthHeight * 0.6f);
            //preMonthBtnPath.moveTo(monthChangeWidth / 2f, monthHeight / 2f);
            //preMonthBtnPath.rLineTo(btnHeight / 2f, -btnHeight / 2f);
            //preMonthBtnPath.rLineTo(0, btnHeight);
            //preMonthBtnPath.close();
            //nextMonthBtnPath = new Path();
            //nextMonthBtnPath.moveTo(width - monthChangeWidth / 2f,
            //      monthHeight / 2f);
            //nextMonthBtnPath.rLineTo(-btnHeight / 2f, -btnHeight / 2f);
            //nextMonthBtnPath.rLineTo(0, btnHeight);
            //nextMonthBtnPath.close();
            monthChangeBtnPaint = new Paint();
            monthChangeBtnPaint.setAntiAlias(true);
            monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            monthChangeBtnPaint.setColor(btnColor);
            cellBgPaint = new Paint();
            cellBgPaint.setAntiAlias(true);
            cellBgPaint.setStyle(Paint.Style.FILL);
            cellBgPaint.setColor(cellSelectedColor);
        }
    }

    /*
    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        // TODO Auto-generated method stub

    }
    */



    /*
    @Override
    public void onError(int requestID, String errorCode, String errorDesc) {
        // TODO Auto-generated method stub

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
        // 取得当天的通知
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
                      String strId = item.getString("TEXT");
                      String strSendDate = item.getString("TEXT");
                      String strIcon = item.getString("TEXT");
                      if (m_MapDayIcons.containsKey(strSendDate))
                      {
                          DisCaleIcons objDisInfo = m_MapDayIcons.get(strSendDate);
                          objDisInfo.PutIcons(strIcon);
                      }else {
                          DisCaleIcons objDisInfo =  new DisCaleIcons();
                          objDisInfo.m_strDate = strSendDate;
                          objDisInfo.PutIcons(strIcon);
                      }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;

        default:
            break;
        }
    }
    */

}


class FlingCalendar implements  GestureDetector.OnGestureListener
{
    class StatusCount{
        public int m_nCount = 0;
        public int m_nStatus = 0;
    }

    public Context ctx;
    public CalendarView viewCalendar;
    private StatusCount statusCount;
    private int nClickCount = 0;
    public CalendarMoveView m_pareMoveView;

    private Thread threadShowDel;
    private boolean bTheadShowDel=false;
    private Handler handler;

    public void InitHandle()
    {
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                float yPos = (float)m_pareMoveView.m_layout_foot.getTop();
                		
                float nMoveDistance = 10;

                if (msg.what == 0x101)
                {
                    if (yPos>0)
                    {
                        if (yPos - nMoveDistance<0)
                        {
                            nMoveDistance = yPos;
                        }
                        yPos = yPos - nMoveDistance;
                        m_pareMoveView.SetYPos(-nMoveDistance);
                    } else {
                        bTheadShowDel =false;
                        //StopThread_ShowDel();
                    }
                }
                super.handleMessage(msg);
            }
        };
    }
    public void InitThreadShowDel()
    {
        bTheadShowDel =false;
        threadShowDel = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while ( (threadShowDel!=null) &&
                        (!threadShowDel.isInterrupted()))
                {
                    Message m = handler.obtainMessage();
                    m.what = 0x101;
                    handler.sendMessage(m);
                    try
                    {
                        Thread.sleep(20);
                    }catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void StopThread_ShowDel()
    {
        if (threadShowDel !=null)
        {
            threadShowDel.interrupt();
            threadShowDel = null;
        }
    }


    public FlingCalendar(Context context
            ,CalendarView argView)
    {
        ctx = context;
        viewCalendar = argView;
        statusCount = new StatusCount();

        InitHandle();
        InitThreadShowDel();
    }

    public void SetParent(CalendarMoveView argMoveViewParent)
    {
        m_pareMoveView = argMoveViewParent;
    }


    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        nClickCount = nClickCount+1;
        if (nClickCount>1000)
        {
            nClickCount = 0;
        }
        /*
        float fHeight = (float)(m_pareMoveView.m_calendar_middle.surface.height);
        m_pareMoveView. m_layout_header.setY(-fHeight);
        m_pareMoveView.m_layout_foot.setY(fHeight);
        */

        return false;
    }

    @Override
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
            float arg3) {
        // TODO Auto-generated method stub
        MoveMonth(arg0, arg1);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
            float arg3) {
        MoveMonth(arg0, arg1);
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        viewCalendar.setSelectedDateByCoor(arg0.getX(), arg0.getY());
        viewCalendar.selectedStartDate = viewCalendar.selectedEndDate = viewCalendar.downDate;
        viewCalendar.onItemClickListener.OnItemClick(viewCalendar.selectedStartDate);

        return false;
    }

    public void  MoveMonth(MotionEvent arg0
            , MotionEvent arg1)
    {
        if (statusCount.m_nCount==nClickCount)
        {
            return ;
        }else {
            if (m_pareMoveView.m_bIsMove)
            {
                return;
            }
            statusCount.m_nCount =nClickCount;
            CaledarMoveTask moveTask = new  CaledarMoveTask(m_pareMoveView);
            // move down
            if (arg1.getY() - arg0.getY()>0)
            {
                 moveTask.SetMoveDowUp(0);
            // move up
            }else {
                moveTask.SetMoveDowUp(1);
            }
            String server ="1234";
            String method="1234";
            String msg="1234";
            m_pareMoveView.m_bIsMove =true;
            moveTask.execute(server,method,msg);

            /*
                    // down
                if (arg1.getY() - arg0.getY()>0)
                {
                    m_pareMoveView.m_calendar_middle.clickLeftMonth();
                    m_pareMoveView.m_calendar_Header.clickLeftMonth();
                }else {
                    m_pareMoveView.m_calendar_middle.clickRightMonth();
                    m_pareMoveView.m_calendar_Header.clickRightMonth();
                }
                */

        }
        /*
        if (bTheadShowDel == false)
        {
            bTheadShowDel = true;
            threadShowDel.start();
        }
        */
        //m_pareMoveView.SetYPos(arg1.getY()- arg0.getY());
    }
};



