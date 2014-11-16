package com.infodeliver.client.edu.calendar;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.infodeliver.client.ui.CalendarMoveView;

@SuppressLint("NewApi") public class CaledarMoveTask extends AsyncTask<String, Integer, Bundle> 
{
    public CalendarMoveView m_pareMoveView;
    
    private int mRequestID;
    private int mnDownUp = 0; // 0:down 1:up
    public  float nMoveDistance = 0;
    public float mMiddlePos = 0;
    
    public void SetMoveDowUp(int argDownUp)
    {
        	
    	mnDownUp = argDownUp;
    }
    
    public CaledarMoveTask(CalendarMoveView argView) {
        m_pareMoveView = argView;
    
    }
    
    @Override  
    protected void onPreExecute() {
    	
    	m_pareMoveView.SetBeginMovePos(mnDownUp);
    	//m_pareMoveView.init_CalendarPos();
    	//int lxk =123;
    } 
    
    @Override
    protected Bundle doInBackground(String... params) {
        Bundle result = new Bundle();
        nMoveDistance = 20;
        
        // up 
        if (mnDownUp == 1)
        {
        	try {
		        int  yPos = m_pareMoveView.m_layout_header.getTop();
		        while (yPos>0)
		        {
					publishProgress(1); 
					Thread.sleep(30);
					yPos = m_pareMoveView.m_layout_header.getTop();
		        }
					
        	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
	     //down   
        }else  if (mnDownUp == 0)
        {
        	try {
        		float yPos = m_pareMoveView.m_layout_header.getTop();
		        while (yPos<0)
		        {
					publishProgress(1); 
					Thread.sleep(30);
					yPos = m_pareMoveView.m_layout_header.getTop();
		        }
					
        	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
        	
        }
        
        return result;
    }
    
    @Override  
    protected void onProgressUpdate(Integer... values) {
    	 // up
    	 if (mnDownUp == 1)
    	 {
		        float yPos = m_pareMoveView.m_layout_header.getTop();
		        float nMoveDistance1 = 20;
		        if (yPos>0)
		        {
		        	if (yPos - nMoveDistance<=0)
					{
		        		nMoveDistance1 = yPos;
					}
		        	m_pareMoveView.SetYPos(-nMoveDistance1);
		        }
    		 
         // down
    	 }else if (mnDownUp == 0)
    	 {
		        float yPos = m_pareMoveView.m_layout_header.getTop();
		        float nMoveDistance1 = 20;
		        if (yPos<0)
		        {
		        	if (yPos + nMoveDistance>=0)
					{
		        		nMoveDistance1 = -yPos;
					}
		        	m_pareMoveView.SetYPos(nMoveDistance1);
		        }
    	 }
    }  
    
    @Override
    protected void onPostExecute(Bundle result) 
    {
    	
    	m_pareMoveView.ChangeView();
    	
    	// up 
   	 	if (mnDownUp == 1)
   	 	{
   	 		m_pareMoveView.m_calendar_middle.clickRightMonth();
   	 		m_pareMoveView.m_calendar_Header.clickRightMonth();   	 	
   	 		// down
   	 	}else if (mnDownUp == 0)
   	 	{
   	 		m_pareMoveView.m_calendar_middle.clickLeftMonth();
   	 		m_pareMoveView.m_calendar_Header.clickLeftMonth();
   	 	}
   	 	m_pareMoveView.m_bIsMove =false;
    	
    }

}