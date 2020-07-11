package am.android.manager;

import java.io.File;

import am.android.consts.Consts;
import android.os.Environment;

public class FileManager
{	
	
	public static boolean isSDCardExist()
	{
		boolean result = true;
		if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
		     result=false;
		}
		return result;
	}
	
	public static boolean makeDirectory()
	{
		boolean isSDCardExist = isSDCardExist();
		
		if(isSDCardExist)
		{
		    new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"//PositionAssistant").mkdir();
	 	    new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"//PositionAssistant//Databases").mkdir();
	 	    new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"//PositionAssistant//Pictures").mkdir();
		}
		
		return isSDCardExist;		
	}
	
	public static void makeFile()
	{
		Consts.file_settings   = new File(Consts.PATH_DATABASE+"//settings3.db");
		Consts.file_sms_record = new File(Consts.PATH_DATABASE+"//sms.db");
	}	
}
