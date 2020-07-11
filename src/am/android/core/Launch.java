package am.android.core;

import java.util.ArrayList;
import java.util.Date;

import am.android.cell.SMSInfor;
import am.android.consts.Consts;

public class Launch 
{
	
	/*public static void combineSMSInfor()
    {
    	//抽取每天的第一条数据进行组合
    	Consts.listdata_smsinfor_show.clear();
   
    	String temp = "";
    	
    	for(int i=0;i<Consts.listdata_smsinfor.size();i++)
    	{
    		if(!Consts.listdata_smsinfor.get(i).getDate().equals(temp))
    		{
    			temp = Consts.listdata_smsinfor.get(i).getDate();
    			Consts.listdata_smsinfor_show.add(Consts.listdata_smsinfor.get(i));
    		}
    	}
    	
    }*/
	
	public static void combineSMSInfor()
    {
    	//抽取每天的最后一条数据进行组合
    	Consts.listdata_smsinfor_show.clear();
   
    	String temp = "";
    	
    	for(int i=Consts.listdata_smsinfor.size()-1;i>=0;i--)
    	{
    		if(!Consts.listdata_smsinfor.get(i).getDate().equals(temp))
    		{
    			temp = Consts.listdata_smsinfor.get(i).getDate();
    			Consts.listdata_smsinfor_show.add(Consts.listdata_smsinfor.get(i));
    		}
    	}
    	
    	inverseArrayList(Consts.listdata_smsinfor_show);
    	
    }
	
	
	
	/*public static ArrayList<SMSInfor> inverseArrayList(ArrayList<SMSInfor> listdata)
	{
		ArrayList<SMSInfor> listdata_result = new ArrayList<SMSInfor>();
		
		for(int i = listdata.size()-1; i>=0; i--)
		{
			listdata_result.add(listdata.get(i));
		}
		
		return listdata_result;
		
	}*/
	
	
	public static void inverseArrayList(ArrayList<SMSInfor> listdata)
	{
		ArrayList<SMSInfor> listdata_result = new ArrayList<SMSInfor>();
		
		for(int i = listdata.size()-1; i>=0; i--)
		{
			listdata_result.add(listdata.get(i));
		}
		
		listdata.clear();
		
		for(int j=0;j<listdata_result.size();j++)
		{
			listdata.add(listdata_result.get(j));
		}
		
	}
	
	
	 public static void extractSMSInfor()
	    {
	    	//获取单天所有数据
	    	Consts.listdata_smsinfor_detail.clear();
	    	
	    	boolean isLocked = false;
	    	
	    	for(int i=0; i<Consts.listdata_smsinfor.size();i++)
	    	{
	    		if(Consts.listdata_smsinfor.get(i).getDate().equals(Consts.DATA_SHOW_DETAIL))
	    		{
	    			isLocked = true;
	    			Consts.listdata_smsinfor_detail.add(Consts.listdata_smsinfor.get(i));
	    		}
	    		
	    		if(isLocked)
	    		{
	    			if(!Consts.listdata_smsinfor.get(i).getDate().equals(Consts.DATA_SHOW_DETAIL))
	    			{
	    				break;
	    			}
	    		}
	    		
	    	} 	
	    }
	
	
	public static void sortSMSInfor()
    {
    	//对原始列表进行排序
    	SMSInfor si = new SMSInfor();
    	
    	try
    	{
    	
    	    for(int i=0; i<Consts.listdata_smsinfor.size(); i++)
    	    {
    		    for(int j=i; j<Consts.listdata_smsinfor.size();j++)
    		    {
    			    int idate =Integer.valueOf(Consts.listdata_smsinfor.get(i).getDate());
    			    int jdate =Integer.valueOf(Consts.listdata_smsinfor.get(j).getDate());
    			
    			    if(idate>jdate)
    			    {
    				    //排序
    				    si = Consts.listdata_smsinfor.get(i);
    				    Consts.listdata_smsinfor.set(i, Consts.listdata_smsinfor.get(j));
    				    Consts.listdata_smsinfor.set(j, si);	
    			    }
	
    		    }
    	    }
    	}
    	catch(Exception ex)
		{
	
		}
    	
    }
	
	
	
	public static String getSystemDate()
	{
		
		Date   date = new Date(System.currentTimeMillis());
		String result = Consts.sdf_str.format(date);  
		
		return result;
	}
	
	public static String getSystemTime()
	{
		Date   date = new Date(System.currentTimeMillis());
		String str_result = Consts.sdf.format(date);  
		String result     = str_result.substring(8, 14);
		return result;
	}
	
	
}
