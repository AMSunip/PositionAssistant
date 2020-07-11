package am.android.manager;

import java.math.BigDecimal;
import java.util.Calendar;

import am.android.consts.State;
import android.widget.EditText;
import android.widget.TextView;

public class FormatManager 
{
	
	public static int getAccuracyFormat(double raw)
    {
    	int result = 0;
    	result =(int)(raw+0.5);   //四舍五入
    	return result;    	
    }
    
    /*public static double getAltitudeFormat(double raw)
    {
    	double result = 0;
    	result = ((int)(raw*1000))/1000.0;   //保留3位小数
    	return result;  
    }*/
    
    public static int getAltitudeFormat(double raw)
    {
    	int result = 0;
    	result =(int)(raw+0.5);   //四舍五入
    	return result;  
    }
    
    public static String getContract(String Contract,int N)
    {
    	String result = Contract;
    	
    	if(Contract.equals(""))
    	{
    		result="联系人"+ String.valueOf(N);
    	}
    	
    	return result;    	
    }
    
    public static String getDivider()
    {
    	String result = "";
    	
    	if(State.STATE_DIVIDER.equals("1"))
    	{
    		result=",";
    	}
    	else if(State.STATE_DIVIDER.equals("2"))
    	{
    		result=";";
    	}
    	else if(State.STATE_DIVIDER.equals("3"))
    	{
    		result=":";
    	}
    	else
    	{
    		result = " "+" ";
    	}
    	
    	return result;
    }
        
    public static String getDoubleString(int raw)
    {
    	String result= "";    	
    	result=String.valueOf(raw);
    	
    	if(raw<10)
    	{
    		result="0"+result;
    	}	
    	
    	return result;
    }   
    
    public static String getEditTextStringFormat(EditText et_text)
    {
    	String result = "";
    	result=et_text.getText().toString();
    	
    	if(result.equals(""))
    	{
    		result="0";
    	}    	
    	return result;
    }
    
    public static String getEditTextStringFormat(String et_str)
    {
    	String result = "";
    	result=et_str;
    	
    	if(result.equals(""))
    	{
    		result="0";
    	}    	
    	return result;
    }
    
    
    /*public static String getMessageFormat(Location location)
    {    	
    	  String result = "";    	 
    	  result = getSystemTimeString()+" "+" "+String.valueOf(location.getLatitude())+getDivider()+String.valueOf(location.getLongitude());
    	  return result;
    }*/
    
    public static String getMessageFormat(String latitude,String longitude)
    {
    	  String result = ""; 
    	  result = latitude + getDivider() + longitude;
    	  return result;
    }
    
    
    /******公版******/
    public static String getMessageFormatForDetail(String latitude,String longitude,String altitude,String accuracy)
    {
    	  String result = "";     	 
    	  result =   getSystemTimeString() + getDivider() 
    	            +latitude              + getDivider() 
    	            +longitude             + getDivider()
    	            +altitude              + getDivider()
    	            +accuracy;
    	  return result; 	
    }
    /******公版******/
    
    
    /******内测版******/
    /*public static String getMessageFormatForDetail(String latitude,String longitude,String altitude,String accuracy)
    {
    	  String result = "";     	 
    	  result =  getSystemTimeString() + getDivider() 
    	           +altitude              + getDivider()
    	           +accuracy              + getDivider() 
    	           +latitude              + getDivider() 
    	           +longitude;  	          
    	          
    	  return result; 	
    }/*
    /******内测版******/
    
	
    public static double getPositionFormat(double raw)
    {
    	//保留8位小数
    	double result = 0;
        BigDecimal decimal = new BigDecimal(raw);
        result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
    	return result;    
    }
    
    public static String getSystemTimeString()
    {
    	Calendar calendar = Calendar.getInstance(); 
    	
    	int year  = calendar.get(Calendar.YEAR);
    	int month = calendar.get(Calendar.MONTH)+1;
    	int day   = calendar.get(Calendar.DAY_OF_MONTH);
    	int hour  = calendar.get(Calendar.HOUR_OF_DAY);
    	int minute= calendar.get(Calendar.MINUTE);
    	int second= calendar.get(Calendar.SECOND);
    	String result = "";
    	result = String.valueOf(year)+getDoubleString(month)+getDoubleString(day)
    	                             +getDoubleString(hour)+getDoubleString(minute)+getDoubleString(second);    	
    	return result;
    }
    
    public static String getTextViewStringFormat(TextView tv_text)
    {
    	String result = "";
    	result=tv_text.getText().toString();
    	
    	if(result.equals(""))
    	{
    		result="0";
    	}
    	
    	return result;
    }   
    
    public static String getTextViewStringFormat(String tv_str)
    {
    	String result = "";
    	result=tv_str;
    	
    	if(result.equals(""))
    	{
    		result="0";
    	}
    	
    	return result;
    }   
    
    
}
