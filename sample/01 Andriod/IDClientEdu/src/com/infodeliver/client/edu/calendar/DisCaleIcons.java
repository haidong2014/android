package com.infodeliver.client.edu.calendar;
public class DisCaleIcons {
	public String m_nkeyId;
	public String m_strDate;
	public String m_nIconId_1;
	public String m_nIconId_2;
	public String m_nIconId_3;
	public String m_nIconId_4;
	public int m_nIconCount;
	public DisCaleIcons()
	{
		m_nIconCount = 0;
		m_nIconId_1 = "0";
		m_nIconId_2 = "0";
		m_nIconId_3 = "0";
		m_nIconId_4 = "0";
		
		
	}
	
	public void PutIcons(String nargIconId)
	{
		if (m_nIconCount == 0)
		{
			m_nIconId_1 = nargIconId;
			m_nIconCount = 1;
		}else if (m_nIconCount == 1)
		{
			m_nIconId_2 = nargIconId;
			m_nIconCount = 2;
		} else if (m_nIconCount == 2)
		{
			m_nIconId_3 = nargIconId;
			m_nIconCount = 3;
		} else if (m_nIconCount == 3)
		{
			m_nIconId_4 = nargIconId;
			m_nIconCount = 4;
		}
	}
}
