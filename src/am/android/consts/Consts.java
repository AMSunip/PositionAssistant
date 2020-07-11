package am.android.consts;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import am.android.cell.SMSInfor;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.os.Environment;

public class Consts
{

	
	public static String PATH_SDCARD                               = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	public static ArrayList<GpsSatellite> numSatelliteList         = new ArrayList<GpsSatellite>();
    
	public static ArrayList<SMSInfor>     listdata_smsinfor        = new ArrayList<SMSInfor>();
	public static ArrayList<SMSInfor>     listdata_smsinfor_detail = new ArrayList<SMSInfor>();
	public static ArrayList<SMSInfor>     listdata_smsinfor_show   = new ArrayList<SMSInfor>();
	
	public static int                     DAYS_RETAIN              = 30;  //保留有数据记录的天数，这里设置为30天
	
	public static SimpleDateFormat        sdf                      = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat        sdf_str                  = new SimpleDateFormat("yyyyMMdd");
	
	public static String                  DATA_SHOW_DETAIL         = "";
	public static String                  FLAG_DATE_START          = "";
	
    public static String                  PATH_DATABASE            = Environment.getExternalStorageDirectory().getAbsolutePath()+"//PositionAssistant//Databases";
	
	public static String                  PATH_INFOR               = Environment.getExternalStorageDirectory().getAbsolutePath()+"//PositionAssistant";
	
	
	public static String                  TITLE_LOG                ="Time"+","+"Latitude"+","+"Longitude"+","+"Altitude"+","+"Accuracy"+","+"Action"+","+"Person1"+","+"Person2"+","+"Person3"+","+"Place"+","+"Remark";
  
	
	public static File                    file_settings            = new File(Consts.PATH_DATABASE+"//settings3.db");	
	
	public static File                    file_sms_record          = new File(Consts.PATH_DATABASE+"//sms.db");
	
    public static File                    file_log                 = new File(Consts.PATH_INFOR+"//log.csv"); 
   
	
    public static int                     COLOR_STYLE_PUBLIC_GREEN = Color.rgb(69, 190, 69);
    
    //主题 - 默认
    public static int                     COLOR_THEME_DEFAULT_TEXT       = Color.rgb(40, 40, 40);
    public static int                     COLOR_THEME_DEFAULT_TEXT2      = Color.rgb(99, 99, 99);
    public static int                     COLOR_THEME_DEFAULT_TEXT_TITLE = Color.rgb(248, 248, 248);
    public static int                     COLOR_THEME_DEFAULT_DIVIDER    = Color.rgb(203, 203, 203);
    
    //主题 - 炫酷黑
    public static int                     COLOR_THEME_BLACK_TEXT         = Color.rgb(248, 248, 248);
    public static int                     COLOR_THEME_BLACK_TEXT2        = Color.rgb(166, 166, 166);
    public static int                     COLOR_THEME_BLACK_DIVIDER      = Color.rgb(80, 80, 80);
     
    
}
