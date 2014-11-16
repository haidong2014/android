package com.infodeliver.client.edu.calendar;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infodeliver.client.edu.R;

public class Adap_CaleInfo extends BaseAdapter {
	private LayoutInflater inflater = null;
    public ArrayList<CaleListItem> arrDuxin = null;      
    private int selectedPosition = -1;  
   
   
    // 保存每项中的控件的引用  
    class ViewHolder {  
        TextView objTtile;  
        TextView objDay;
        TextView objTime;  
        TextView objYmd;  
    }  
    
    public Adap_CaleInfo(Context context
			, ArrayList<CaleListItem> argDuxin) 
    {
    	// LayoutInflater用来加载界面  
    	inflater = LayoutInflater.from(context);  
    	arrDuxin = argDuxin;
    }   
    
    public void setSelectedPosition(int position) {  
        selectedPosition = position;  
    }  
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrDuxin.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrDuxin.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convert, ViewGroup arg2) 
	{

       // TODO Auto-generated method stub  
       ViewHolder holder;  
       if(convert == null)  
       {  
    	   
    	   convert = inflater.inflate(R.layout.calendar_list_items, null);
    	
            holder = new ViewHolder();  
            holder.objTtile = (TextView)convert.findViewById(R.id.cale_info);  
            holder.objDay = (TextView)convert.findViewById(R.id.cale_ymd);
            
            convert.setTag(holder);  
        } else {  
            // 获取包含当前项控件的对象  
            holder = (ViewHolder)convert.getTag();  
        }  
       
       CaleListItem objTempInfo = arrDuxin.get(arg0);
        
        		
        // 设置当前项的内容  
        String strTitle = objTempInfo.strTitle + "(" + objTempInfo.strNumber + ")";
        holder.objTtile.setText(strTitle);  
        holder.objDay.setText(objTempInfo.strYmd);
        				
        return convert;  
	}

}
