package am.android.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import am.android.cell.SMSInfor;
import am.android.consts.Consts;
import am.android.consts.SMS;
import am.android.consts.State;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager
{
	
	public static void deleteItemSMSByDate(ArrayList<SMSInfor> listdata_smsinfor_show,int position)
	{
		SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//sms.db",null, SQLiteDatabase.OPEN_READWRITE);		
			
		database.delete("sms","date=?",new String[]{listdata_smsinfor_show.get(position).getDate().trim()});
		
		database.close();
	}
	
	public static void deleteItemSMSById(ArrayList<SMSInfor> listdata_smsinfor_detail,int position)
	{
		SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//sms.db",null, SQLiteDatabase.OPEN_READWRITE);		
			
		database.delete("sms","_id=?",new String[]{listdata_smsinfor_detail.get(position).getID().trim()});
		
		database.close();
	}
	
	
	public static void fileExist(Context context,String fileName)
	{
		File   file = new File(Consts.PATH_DATABASE+"//"+fileName);
		if(!file.exists())
		{
		 try 
		 {
			file.createNewFile();
			InputStream is=context.getAssets().open(fileName);
			FileOutputStream fos=new FileOutputStream(file);
			byte[] buffer=new byte[2048];
			while(is.read(buffer)!=-1)
			{
				fos.write(buffer);
			}
			fos.close();
			is.close();	
			
		  } 
		 catch (IOException e)
		 {
			e.printStackTrace();
		 }		  
	   }	
	}
	
	
	public static void getMySettings()
	{
		SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//settings3.db", null, SQLiteDatabase.OPEN_READWRITE);
		Cursor cursor=database.rawQuery("select * from settings3", null);
		
		while(cursor.moveToNext())
		{
			 State.STATE_THEME           = cursor.getString(1);
			 State.STATE_DIVIDER         = cursor.getString(2);			 
			 State.STATE_PLUS_SP         = cursor.getString(3);
			 State.STATE_PLUS_CS         = cursor.getString(4);
			 State.STATE_PLUS_CP         = cursor.getString(5);
			 State.STATE_PLUS_RL         = cursor.getString(6);			 
			 State.STATE_FREQUENCY       = cursor.getString(7);
			 State.STATE_CONTRACT1       = cursor.getString(8);
			 State.STATE_NUMBER1         = cursor.getString(9);
			 State.STATE_CONTRACT2       = cursor.getString(10);
			 State.STATE_NUMBER2         = cursor.getString(11);
			 State.STATE_CONTRACT3       = cursor.getString(12);
			 State.STATE_NUMBER3         = cursor.getString(13);
			 State.STATE_ACCURACY_REMIND = cursor.getString(14);
			 State.STATE_ACCURACY        = cursor.getString(15);
		}
		
		database.close();
	}
	
	
	public static void getMySMSInfor()
	{
		SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//sms.db", null, SQLiteDatabase.OPEN_READWRITE);
		Cursor cursor=database.rawQuery("select * from sms", null);
		
		Consts.listdata_smsinfor.clear();
		
		SMSInfor si = new SMSInfor();
		
		while(cursor.moveToNext())
		{
			 SMS.SMS_INFOR_ID     = cursor.getString(0).trim();
			 SMS.SMS_INFOR_DATE   = cursor.getString(1).trim();
			 SMS.SMS_INFOR_TIME   = cursor.getString(2).trim();
			 SMS.SMS_INFOR_NAME   = cursor.getString(3).trim();
			 SMS.SMS_INFOR_NUMBER = cursor.getString(4).trim();			
			 SMS.SMS_INFOR_INFOR  = cursor.getString(5).trim();
			 
			 si = new SMSInfor();
			 si.setData(SMS.SMS_INFOR_ID, SMS.SMS_INFOR_DATE,SMS.SMS_INFOR_TIME,
					    SMS.SMS_INFOR_NAME, SMS.SMS_INFOR_NUMBER,SMS.SMS_INFOR_INFOR);
			 
			 Consts.listdata_smsinfor.add(si); 
		}
		
		database.close();
	}
	

	public static void writeDataToSettingFile(Context context,String THEME,String DIVIDER,
                                                              String PLUSSP,String PLUSCS,
                                                              String PLUSCP,String PLUSRL,
                                                              String FREQUENCY,
                                                              String CONTRACT1,String NUMBER1,
                                                              String CONTRACT2,String NUMBER2,
                                                              String CONTRACT3,String NUMBER3,
                                                              String ACCURACYREMIND,String ACCURACY)
    {
       try
       {
         fileExist(context,"settings3.db");    
         ContentValues cv=new ContentValues();
         cv.put("theme", THEME);
         cv.put("divider", DIVIDER);
         cv.put("plussp", PLUSSP);
         cv.put("pluscs", PLUSCS);
         cv.put("pluscp", PLUSCP);
         cv.put("plusrl", PLUSRL);
         cv.put("frequency",FREQUENCY);
         cv.put("contract1", CONTRACT1);
         cv.put("number1", NUMBER1);
         cv.put("contract2", CONTRACT2);
         cv.put("number2", NUMBER2);
         cv.put("contract3", CONTRACT3);
         cv.put("number3", NUMBER3);
         cv.put("accuracyremind", ACCURACYREMIND);
         cv.put("accuracy", ACCURACY);


         SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//settings3.db", null,SQLiteDatabase.OPEN_READWRITE);
         database.update("settings3", cv, "_id=?", new String[]{"1"});
         database.close();  
       }
       catch(Exception ex)
       {

       }
    }
	
   
	public static void deleteSMSData()
	{
		SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//sms.db",null, SQLiteDatabase.OPEN_READWRITE);		
		
		for(int i = 0; i<Consts.listdata_smsinfor.size(); i++)
		{
			database.delete("sms","_id=?",new String[]{Consts.listdata_smsinfor.get(i).getID().trim()});
		}
		
		database.close();
		
	}
	
	public static void rewriteSMSInfor(Context context)
	{
		int position = 0;
		for(int i=0; i<Consts.listdata_smsinfor.size();i++)
		{
			if(Consts.listdata_smsinfor.get(i).getDate().trim().equals(Consts.FLAG_DATE_START))
			{
				position = i;
				break;
			}
		}
		
		
		fileExist(context,"sms.db"); 
	    SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//sms.db", null,SQLiteDatabase.OPEN_READWRITE);
		
	    ContentValues cv=new ContentValues();
		
		for(int j = position; j<Consts.listdata_smsinfor.size();j++)
		{
			  
		    cv.put("date", Consts.listdata_smsinfor.get(j).getDate().trim());
		    cv.put("time", Consts.listdata_smsinfor.get(j).getTime().trim());
		    cv.put("name", Consts.listdata_smsinfor.get(j).getName().trim());
		    cv.put("number", Consts.listdata_smsinfor.get(j).getName().trim());
		    cv.put("infor", Consts.listdata_smsinfor.get(j).getInfor().trim());	    
		    	
		    database.insert("sms", null, cv); 
		}
		
		 database.close(); 
	}
	
	
	public static void writerDataToSMSFile(Context context,String DATE,String TIME,String NAME, String NUMBER, String INFOR)
	{
	    fileExist(context,"sms.db"); 
	    
	    ContentValues cv=new ContentValues();
	    cv.put("date", DATE);
	    cv.put("time", TIME);
	    cv.put("name", NAME);
	    cv.put("number", NUMBER);
	    cv.put("infor", INFOR);
	    
	    SQLiteDatabase database=SQLiteDatabase.openDatabase(Consts.PATH_DATABASE+"//sms.db", null,SQLiteDatabase.OPEN_READWRITE);
	    database.insert("sms", null, cv);
	    
	    database.close(); 
	    
	}	
	
	
}
