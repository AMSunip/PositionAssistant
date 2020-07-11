package am.android.positionassistant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import am.android.activity.AMapActivity;
import am.android.activity.SMSRecordActivity;
import am.android.adapter.MyViewPagerAdapter;
import am.android.broadcast.SMSDeliveredReceiver;
import am.android.broadcast.SMSSentReceiver;
import am.android.consts.Consts;
import am.android.consts.DeviceInfor;
import am.android.consts.GPSLocation;
import am.android.consts.State;
import am.android.core.Launch;
import am.android.csv.CSVWriter;
import am.android.dialog.MyDialogAccuracy;
import am.android.dialog.MyDialogAddLog;
import am.android.dialog.MyDialogContract;
import am.android.manager.AnimationManagerSystem;
import am.android.manager.DatabaseManager;
import am.android.manager.FileManager;
import am.android.manager.FormatManager;
import am.android.manager.GpsManager;
import am.android.manager.InforToast;
import am.android.manager.MobileNumberManager;
import am.android.msg.MessageSender;
import am.android.view.MyMenuItemView;
import am.android.window.MyShareWindow;
import am.android.window.MyShareWindow2;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PositionAssistantActivity extends Activity implements OnClickListener,GpsStatus.Listener,LocationListener
{
	
	private ArrayList<View>          mListViews                    = null;
	private ArrayList<String>        mTitleList                    = null;
	
	private Button                   btn_add_log                   = null;
	private Button                   btn_copy_position             = null;
	private Button                   btn_copy_to_paster            = null;
	private Button                   btn_exit                      = null;
	private Button                   btn_launch_map                = null;
	private Button                   btn_save_setting              = null;
	private Button                   btn_send_position             = null;	
	
	private CheckBox                 cb_accuracy_remind            = null;
	private CheckBox                 cb_plus_add_log               = null;
	private CheckBox                 cb_plus_copy_position         = null;
	private CheckBox                 cb_plus_copy_to_paster        = null;
	private CheckBox                 cb_plus_send_position         = null;
	
	private ClipboardManager         myClipboard                   = null;
	private ClipData                 myClip                        = null;
	
	private EditText                 et_latitude                   = null;
	private EditText                 et_longitude                  = null;	
	
	private ImageView                iv_cursor                     = null;
	private ImageView                iv_gps                        = null;
   
	private LinearLayout             layout_main                   = null;
	private LinearLayout             layout_main_divider           = null;
	private LinearLayout             layout_mainpage_divider1      = null;
	private LinearLayout             layout_mainpage_divider2      = null;
	private LinearLayout             layout_mainpage_divider3      = null;
	private LinearLayout             layout_mainpage_divider4    = null;
	
	private LinearLayout             layout_setting_divider1       = null;
	private LinearLayout             layout_setting_divider2       = null;
	private LinearLayout             layout_setting_divider3       = null;
	private LinearLayout             layout_setting_divider4       = null;
	private LinearLayout             layout_setting_divider5       = null;
	private LinearLayout             layout_setting_divider6       = null;
	private LinearLayout             layout_setting_divider7       = null;
	private LinearLayout             layout_setting_divider8       = null;
	private LinearLayout             layout_setting_divider9       = null;
	private LinearLayout             layout_setting_divider10      = null;
	private LinearLayout             layout_setting_divider11      = null;
	private LinearLayout             layout_setting_divider12      = null;
	private LinearLayout             layout_setting_divider13      = null;
	private LinearLayout             layout_setting_divider14      = null;
	private LinearLayout             layout_setting_divider15      = null;
	private LinearLayout             layout_setting_divider16      = null;
	private LinearLayout             layout_setting_divider17      = null;
	private LinearLayout             layout_setting_divider18      = null;
	private LinearLayout             layout_setting_divider19      = null;
	private LinearLayout             layout_setting_divider20      = null;
		
	private LinearLayout             layout_accuracy_extend        = null;
	private LinearLayout             layout_divider_extend         = null;
	private LinearLayout             layout_gps_extend             = null;
	private LinearLayout             layout_plus_extend            = null;
	private LinearLayout             layout_refresh_extend         = null;
	private LinearLayout             layout_sms_extend             = null;
	private LinearLayout             layout_theme_extend           = null;
	
	private MyDialogAddLog           dialog_add_log                = null;
	private MyDialogContract         dialog_contract1              = null;
	private MyDialogContract         dialog_contract2              = null;
	private MyDialogContract         dialog_contract3              = null;
	private MyDialogAccuracy         dialog_accuracy               = null;
	
	private MyMenuItemView           mmiv_item1                    = null;
	private MyShareWindow            my_share_window               = null;
	private MyShareWindow2           my_share_window2              = null;
	private MyViewPagerAdapter       adapter                       = null;
	
	
    private PagerTabStrip            pagerTabStrip                 = null;    
    
    private RadioButton              rb_theme_white                = null;
	private RadioButton              rb_theme_black                = null;
	private RadioButton              rb_divider_space              = null;
	private RadioButton              rb_divider_comma              = null;
	private RadioButton              rb_divider_semicolon          = null;
	private RadioButton              rb_divider_colon              = null;
	private RadioButton              rb_frequency_every1s          = null;
	private RadioButton              rb_frequency_every2s          = null;
	private RadioButton              rb_frequency_every3s          = null;
	private RadioButton              rb_frequency_every5s          = null;
	
	private RadioGroup               rg_divider                    = null;
	private RadioGroup               rg_frequency                  = null;
	private RadioGroup               rg_themesetting               = null;
    
	private RelativeLayout           layout_accuracy_remind_custom = null;
    private RelativeLayout           layout_gps                    = null;
    private RelativeLayout           layout_sms_record10           = null;
    private RelativeLayout           layout_title                  = null;
    
    private SMSDeliveredReceiver     smsDeliveredReceiver1         = null;
    private SMSDeliveredReceiver     smsDeliveredReceiver2         = null;
    private SMSDeliveredReceiver     smsDeliveredReceiver3         = null;
    private SMSSentReceiver          smsSentReceiver               = null;
	
    private TextView                 tv_accuracy_remind            = null;
    private TextView                 tv_accuracy_remind_custom     = null;
    private TextView                 tv_copyright                  = null;
	private TextView                 tv_gps                        = null;
	private TextView                 tv_gps_accuracy               = null;
	private TextView                 tv_gps_altitude               = null;
	private TextView                 tv_gps_number                 = null;
	private TextView                 tv_gps_setting                = null;
	private TextView                 tv_latitude                   = null;
	private TextView                 tv_longitude                  = null;
	private TextView                 tv_mainpage                   = null;
	private TextView                 tv_position_divider           = null;
	private TextView                 tv_position_plus              = null;
	private TextView                 tv_refresh_setting            = null;
	private TextView                 tv_satellite_accuracy         = null;
	private TextView                 tv_satellite_altitude         = null;
	private TextView                 tv_satellite_number           = null;
	private TextView                 tv_setting                    = null;	
	private TextView                 tv_sms_record                 = null;
	private TextView                 tv_sms_record10               = null;
	private TextView                 tv_theme_setting              = null;
	private TextView                 tv_title                      = null;
	
    private View                     view1                         = null;
	private View                     view2                         = null;
	private ViewPager                mViewPager                    = null; 	
	
	private boolean                  isAccuracyExtend              = true;
	private boolean                  isDividerExtend               = true;
	private boolean                  isGPSExtend                   = true;
	private boolean                  isPlusExtend                  = true;
	private boolean                  isRefreshExtend               = true;
	private boolean                  isSMSRecordExtend             = true;
	private boolean                  isThemeExtend                 = true;
	
	private int                      count                         = 0;
	private int                      offset                        = 0;
	private int                      currIndex                     = 0;
	private int                      ONETAB                        = 0;	
	private int                      bmpW                          = 0;
	
	private long                     exitTime                      = 0;
	private static final int         TWO_MINUTES                   = 1000 * 60 * 2;
	
	private DisplayMetrics           dm                            = null;
	private Matrix                   matrix                        = null;
	
	private LocationManager          locationManager               = null;
	private Location                 location                      = null;
	
    private Handler                  handler_gps                   = new Handler();
     
    private Runnable                 run_gps                       = new Runnable() 
    {
		
		@Override
		public void run()
		{
		   handler_gps.postDelayed(run_gps, 500);
		   
		   if(GpsManager.isGPSEnable(PositionAssistantActivity.this))
		   {
			   count++;
			   if(count%2==0)
			   {
				   iv_gps.setBackgroundResource(R.drawable.gps_a);
			   }
			   else
			   {
				   iv_gps.setBackgroundResource(R.drawable.gps_b);
			   }
			   
			   if(count>999)
			   {
				   count=0;
			   }	  			   
			 		
			   if(State.STATE_THEME.equals("1"))
			   {
				   //炫酷黑
				   tv_gps_number.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);			   
				   tv_gps_number.setText(String.valueOf(GpsManager.getAlmanac(Consts.numSatelliteList))+"/"+String.valueOf(Consts.numSatelliteList.size()));
				   //tv_gps_accuracy.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
				   tv_gps_altitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
				   
				   if((State.STATE_ACCURACY_REMIND.equals("1"))&&(!isBetterAccuracy(State.STATE_ACCURACY)))
				   {
					   tv_gps_accuracy.setTextColor(Color.RED);
				   }
				   else
				   {
					   tv_gps_accuracy.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
				   }
				   
			   }
			   else
			   {
				   //简约白
				   tv_gps_number.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);			   
				   tv_gps_number.setText(String.valueOf(GpsManager.getAlmanac(Consts.numSatelliteList))+"/"+String.valueOf(Consts.numSatelliteList.size()));
				  // tv_gps_accuracy.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);
				   tv_gps_altitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);
				   
				   
				   if((State.STATE_ACCURACY_REMIND.equals("1"))&&(!isBetterAccuracy(State.STATE_ACCURACY)))
				   {
					   tv_gps_accuracy.setTextColor(Color.RED);
				   }
				   else
				   {
					   tv_gps_accuracy.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
				   }
				   
				   
			   }  
			
		   }
		   else
		   {
			   //GPS关闭的时候精度提醒无效
			   tv_gps_number.setTextColor(Color.RED);
			   tv_gps_number.setText("0");			   
			   tv_gps_accuracy.setTextColor(Color.RED);
			   tv_gps_altitude.setTextColor(Color.RED);
			   
			   iv_gps.setBackgroundResource(R.drawable.arrextend);
		   }
		   
		}
	};
	
	
	
	@Override	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_position_assistant);
	
		init();
		
	}

	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		locationManager.removeUpdates(this);
	}	
	
	
	public void onPause() 
	{
	     super.onPause();
	     
	     //locationManager.removeUpdates(this);	    
	     
	     unregisterReceiver(smsSentReceiver);
	     unregisterReceiver(smsDeliveredReceiver1);
	     unregisterReceiver(smsDeliveredReceiver2);
	     unregisterReceiver(smsDeliveredReceiver3);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
		
		smsDeliveredReceiver1 = new SMSDeliveredReceiver();
		smsDeliveredReceiver2 = new SMSDeliveredReceiver();
		smsDeliveredReceiver3 = new SMSDeliveredReceiver();
		
	    smsSentReceiver      = new SMSSentReceiver();
	    
	    registerReceiver(smsSentReceiver, new IntentFilter("SMS_SENT"));
	    registerReceiver(smsDeliveredReceiver1, new IntentFilter("SMS_DELIVERED1"));
	    registerReceiver(smsDeliveredReceiver2, new IntentFilter("SMS_DELIVERED2"));
	    registerReceiver(smsDeliveredReceiver3, new IntentFilter("SMS_DELIVERED3"));
	}
	
	
	@Override
	public void onRestart()
	{
		super.onRestart();	
		setPositionProvider(Double.valueOf(getCurrentFrequency()));
	}
	
	
	private boolean canSendMessage(boolean isNumber1,boolean isNumber2,boolean isNumber3)
	{
		boolean result = true;
		
		if((!isNumber1)&&(!isNumber2)&&(!isNumber3))
		{
			result=false;
		}
		
		return result;
		
	}
	
	
	/**
     * @param location
     * @param currentLoaction
     * @return 返回better Loaction
     */
    private Location checkLocation(Location location,Location currentLoaction)
    {
    	
        if (isBetterLocation(location, currentLoaction))
        {
        	  return location;
        }
          
        else
        {
        	  return currentLoaction;
        }
          
    }
	
	
    @SuppressLint("NewApi")
	private void copy(String text)
	{
	     myClip = ClipData.newPlainText("text", text);
	     myClipboard.setPrimaryClip(myClip);   
	}

	
	/*private Criteria getCriteria()
	{  
        Criteria criteria=new Criteria();  
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细   
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);      
        //设置是否要求速度  
        criteria.setSpeedRequired(false);  
        // 设置是否允许运营商收费    
        criteria.setCostAllowed(false);  
        //设置是否需要方位信息  
        criteria.setBearingRequired(false);  
        //设置是否需要海拔信息  
        criteria.setAltitudeRequired(true);  
        // 设置对电源的需求    
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
        return criteria;  
    }  */
	
	
	/**
     * Determines whether one Location reading is better than the current
     * Location fix
     *
     * @param location
     *            The new Location that you want to evaluate
     * @param currentBestLocation
     *            The current Location fix, to which you want to compare the new
     *            one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation)
    {
        if (currentBestLocation == null)
        {
            // A new location is always better than no location
            return true;
        }
 
        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;
 
        // If it's been more than two minutes since the current location, use
        // the new location
        // because the user has likely moved
        if (isSignificantlyNewer)
        {
            return true;
            // If the new location is more than two minutes older, it must be
            // worse
        }
        else if (isSignificantlyOlder)
        {
            return false;
        }
 
        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
 
        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());
 
        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate)
        {
            return true;
        }
        else if (isNewer && !isLessAccurate)
        {
            return true;
        }
        else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
        {
            return true;
        }
        return false;
    }
 
    
    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2)
    {
        if (provider1 == null)
        {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
	
	
	private String getCurrentFrequency()
	{
		String result = getFrequency();    //默认值
		
		return result;
	}
	
	
	private String getCustomAccuracyText(String accuracy)
	{
		String result = "";
		result="自定义提醒精度"+"("+String.valueOf(accuracy)+"m"+")";
		return result;
	}
	
	private String getDivider()
	{
		String result = "0";
		
		if(rg_divider.getCheckedRadioButtonId()==rb_divider_comma.getId())
		{
			result="1";
		}
		else if(rg_divider.getCheckedRadioButtonId()==rb_divider_semicolon.getId())
		{
			result="2";
		}
		else if(rg_divider.getCheckedRadioButtonId()==rb_divider_colon.getId())
		{
			result="3";
		}
		else if(rg_divider.getCheckedRadioButtonId()==rb_divider_space.getId())
		{
			result="0";
		}		
		
		State.STATE_DIVIDER=result;
		
		return result;		
	}
	
	
	private String getFrequency()
	{
		String result="1";
		
		if(rg_frequency.getCheckedRadioButtonId()==rb_frequency_every1s.getId())
		{
			result= "1";
		}
		else if(rg_frequency.getCheckedRadioButtonId()==rb_frequency_every2s.getId())
		{
			result= "2";
		}
		else if(rg_frequency.getCheckedRadioButtonId()==rb_frequency_every3s.getId())
		{
			result= "3";
		}
		else if(rg_frequency.getCheckedRadioButtonId()==rb_frequency_every5s.getId())
		{
			result= "5";
		}
				
		State.STATE_FREQUENCY = result;	
		
		return result;
		
	}
	
	
	private void GetGPSStatus(int event, GpsStatus status)
	{
	     
	    //第一次定位
	    if(event==GpsStatus.GPS_EVENT_FIRST_FIX)
	    {
	    	//InforManager.showInfor(PositionAssistantActivity.this, "第一次定位");
	    }
	    
	    //卫星状态改变
	    if(event==GpsStatus.GPS_EVENT_SATELLITE_STATUS)
	    {
	    	int maxSatellites = status.getMaxSatellites();
	    	int count=0;
	    	Iterator<GpsSatellite> it = status.getSatellites().iterator();
	    	Consts.numSatelliteList.clear();   	
	    	while((it.hasNext()&&(count<maxSatellites)))
	    	{
	    		GpsSatellite s=it.next();
	    		Consts.numSatelliteList.add(s);
	    		count++;
	    	}	   	
	    
	    }   
	    
	    //定位启动
	   if(event==GpsStatus.GPS_EVENT_STARTED)
	   {
		   //InforManager.showInfor(PositionAssistantActivity.this, "卫星定位启动");
	   }
	   
	   //定位结束
	   if(event==GpsStatus.GPS_EVENT_STOPPED)
	   {
		   //InforManager.showInfor(PositionAssistantActivity.this, "卫星定位结束");
	   }
	    
	     
	}
	
	/******公版******/
	private String getLogString(String action)
	{
		String result = "";
		
		result ="'"+ FormatManager.getSystemTimeString()+","
		        +"'"+FormatManager.getEditTextStringFormat(et_latitude)+","
				+"'"+FormatManager.getEditTextStringFormat(et_longitude)+","	
				+FormatManager.getTextViewStringFormat(tv_gps_altitude)+","
			    +FormatManager.getTextViewStringFormat(tv_gps_accuracy)+","
		        +action;
		
		return result;
	}
	/******公版******/
	

	
	private String getLogString(String action,String place,String remark)
	{
		String result = "";
		
		result ="'"+ FormatManager.getSystemTimeString()+","	
		        +"'"+FormatManager.getEditTextStringFormat(GPSLocation.GPS_LOCATION_LATITUDE)+","
				+"'"+FormatManager.getEditTextStringFormat(GPSLocation.GPS_LOCATION_LONGITUDE)+","
				+FormatManager.getTextViewStringFormat(GPSLocation.GPS_LOCATION_ALTITUDE)+","
			    +FormatManager.getTextViewStringFormat(GPSLocation.GPS_LOCATION_ACCURACY)+","
		        +action+","
		        +","+","+","
		        +place+","
		        +remark;
		
		return result;
	}

	
	
	private String getMainMessage1()
	{
		String message = "";
		
	    message =FormatManager.getMessageFormat(et_latitude.getText().toString(),et_longitude.getText().toString());
	
		if(State.STATE_PLUS_SP.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(et_latitude.getText().toString(), et_longitude.getText().toString(), tv_gps_altitude.getText().toString(), tv_gps_accuracy.getText().toString());
		}
	
		return message;
	}
	
	
	private String getMainMessage2()
	{
		String message = "";
		
	    message =FormatManager.getMessageFormat(et_latitude.getText().toString(),et_longitude.getText().toString());
	
		if(State.STATE_PLUS_CS.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(et_latitude.getText().toString(), et_longitude.getText().toString(), tv_gps_altitude.getText().toString(), tv_gps_accuracy.getText().toString());
		}
	
		return message;
	}
	
	
	private String getMainMessage3()
	{
		String message = "";
		
	    message =FormatManager.getMessageFormat(et_latitude.getText().toString(),et_longitude.getText().toString());
	
		if(State.STATE_PLUS_CP.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(et_latitude.getText().toString(), et_longitude.getText().toString(), tv_gps_altitude.getText().toString(), tv_gps_accuracy.getText().toString());
		}
	
		return message;
	}
	
	
	private String getMainMessage4()
	{
		String message = "";
		
	    message =FormatManager.getMessageFormat(GPSLocation.GPS_LOCATION_LATITUDE,GPSLocation.GPS_LOCATION_LONGITUDE);
	
		if(State.STATE_PLUS_RL.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(GPSLocation.GPS_LOCATION_LATITUDE, GPSLocation.GPS_LOCATION_LONGITUDE,GPSLocation.GPS_LOCATION_ALTITUDE, GPSLocation.GPS_LOCATION_ACCURACY);
		}
	
		return message;
	}	
	
	
	private String getMyTheme()
	{
		String result="0";
		
		if(rg_themesetting.getCheckedRadioButtonId()==rb_theme_black.getId())
		{
			result="1";
		}
		else
		{
			result="0";
		}
		
		State.STATE_THEME=result;
		return result;
	}

	
	private void init()
	{
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
		locationManager.addGpsStatusListener(this);
		
		initDatabase();
		
		initView();
		
		initRadioGroup();
		
		initCheckBox();
		
		setMyTheme(State.STATE_THEME);
		
		setCollapseAll();  
		
		initGoogleGPS();
		
		run_gps.run();
		
	}
	
	
	private void initCheckBox()
	{
		setCheckBox(cb_plus_send_position, State.STATE_PLUS_SP);
		setCheckBox(cb_plus_copy_position, State.STATE_PLUS_CS);
		setCheckBox(cb_plus_copy_to_paster, State.STATE_PLUS_CP);
		setCheckBox(cb_plus_add_log, State.STATE_PLUS_RL);	
		setCheckBox(cb_accuracy_remind, State.STATE_ACCURACY_REMIND);
		
		cb_plus_send_position.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					State.STATE_PLUS_SP = "1";
					//InforToast.showInfor(PositionAssistantActivity.this,"CHECKED", State.STATE_THEME);
				}
				else
				{
					State.STATE_PLUS_SP = "0";
					//InforToast.showInfor(PositionAssistantActivity.this,"UNCHECKED", State.STATE_THEME);
				}
				
				save();
			}
		});		
		
		
		cb_plus_copy_position.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					State.STATE_PLUS_CS = "1";					
					//InforToast.showInfor(PositionAssistantActivity.this,"CHECKED", State.STATE_THEME);
				}
				else
				{
					State.STATE_PLUS_CS = "0";
					//InforToast.showInfor(PositionAssistantActivity.this,"UNCHECKED", State.STATE_THEME);
				}
				
				save();
			}
		});
		
		cb_plus_copy_to_paster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					State.STATE_PLUS_CP = "1";
					//InforToast.showInfor(PositionAssistantActivity.this,"CHECKED", State.STATE_THEME);
				}
				else
				{
					State.STATE_PLUS_CP = "0";
					//InforToast.showInfor(PositionAssistantActivity.this,"UNCHECKED", State.STATE_THEME);
				}
				
				save();
			}
		});
		
		
		cb_plus_add_log.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					State.STATE_PLUS_RL = "1";
					//InforToast.showInfor(PositionAssistantActivity.this,"CHECKED", State.STATE_THEME);
				}
				else
				{
					State.STATE_PLUS_RL = "0";
					//InforToast.showInfor(PositionAssistantActivity.this,"UNCHECKED", State.STATE_THEME);
				}
				
				save();
			}
		});		
		
		
		
		cb_accuracy_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				if(isChecked)
				{
					State.STATE_ACCURACY_REMIND = "1";
					//InforToast.showInfor(PositionAssistantActivity.this,"CHECKED", State.STATE_THEME);
				}
				else
				{
					State.STATE_ACCURACY_REMIND = "0";
					//InforToast.showInfor(PositionAssistantActivity.this,"UNCHECKED", State.STATE_THEME);
				}
				
				save();
				
			}
			
		});
		
	}
	
	
	private void initDatabase()
	{
		//初始化时，先判断SD卡是否存在
		//说明：如果SD卡不存在，则不能保存配置信息
		
		//当SD卡存在时
		if(FileManager.isSDCardExist())
		{
			FileManager.makeDirectory();
			FileManager.makeFile();
			initDatabaseFile();			
			
		}
		
		//当SD卡不存在时
		else
		{
			InforToast.showInfor(PositionAssistantActivity.this,  "温馨提示：检测不到SD卡", State.STATE_THEME);
		}
		
		
	}
	

	private void initDatabaseFile()
	{
		
		DatabaseManager.fileExist(PositionAssistantActivity.this, "settings3.db");
		DatabaseManager.fileExist(PositionAssistantActivity.this, "sms.db");
		
		if(Consts.file_settings.exists())
		{
			DatabaseManager.getMySettings();
		}
		else
		{
			
		}	
		
		initSMSInforFile();
		
	}


	private void initSMSInforFile()
	{
		if(Consts.file_sms_record.exists())
		{
			//保留有短信记录的30天数据
			DatabaseManager.getMySMSInfor();
			
			//排序
			Launch.sortSMSInfor();
			
			//获取单天所有数据
			Launch.combineSMSInfor();
			
			//保留最近有数据记录30天的数据，这个时间可以自己定义
			if(Consts.listdata_smsinfor_show.size()>Consts.DAYS_RETAIN)
			{
				//数据
				DatabaseManager.deleteSMSData();
				//计算起始时间点，提取起始时间点到最后一条记录的数据,数据写入数据库
				Consts.FLAG_DATE_START = Consts.listdata_smsinfor_show.get(Consts.listdata_smsinfor_show.size() - Consts.DAYS_RETAIN).getDate().trim();
				
			    DatabaseManager.rewriteSMSInfor(PositionAssistantActivity.this);
				
				//重新读取数据，排序，获取单天所有数据
				DatabaseManager.getMySMSInfor();
				Launch.sortSMSInfor();
				Launch.combineSMSInfor();
			}
			
			
		}
		else
		{
			
		}
	}
	
	
	private void initGoogleGPS()
	{		
		
		//为获取地理位置信息时设置查询条件
		//String bestProvider = locationManager.getBestProvider(getCriteria(), true);  
		 //获取位置信息  
        //如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER  
      
		//location= locationManager.getLastKnownLocation(bestProvider); 
        updateView(location);  
        locationManager.addGpsStatusListener(this);  
        //绑定监听，有4个参数      
        //参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种  
        //参数2，位置信息更新周期，单位毫秒      
        //参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息      
        //参数4，监听      
        //备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新     
          
        // 1秒更新一次，或最小位移变化超过1米更新一次；  
        //注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置  
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);    
        //setPositionProvider();  
        setPositionProvider(Double.valueOf(getCurrentFrequency()));
	}

	
	private void initRadioGroup()
	{
		
		//主题设置
		if(State.STATE_THEME.equals("1"))
		{
			//炫酷黑
			rg_themesetting.check(rb_theme_black.getId());
			
		}
		else
		{
			//简洁白(默认)
			rg_themesetting.check(rb_theme_white.getId());
		}
		
		
		//坐标分隔符
		if(State.STATE_DIVIDER.equals("1"))
		{
			//逗号
			rg_divider.check(rb_divider_comma.getId());
		}
		else if(State.STATE_DIVIDER.equals("2"))
		{
			//分号
			rg_divider.check(rb_divider_semicolon.getId());
		}
		else if(State.STATE_DIVIDER.equals("3"))
		{
			//冒号
			rg_divider.check(rb_divider_colon.getId());
		}
		else
		{
			//空格(默认)
			rg_divider.check(rb_divider_space.getId());
		}
		
		
		//刷新设置
		if(State.STATE_FREQUENCY.equals("1"))
		{
			//每1s刷新一次数据(默认)
			rg_frequency.check(rb_frequency_every1s.getId());
		}
		else if(State.STATE_FREQUENCY.equals("2"))
		{
			//每2s刷新一次数据
			rg_frequency.check(rb_frequency_every2s.getId());
		}
		else if(State.STATE_FREQUENCY.equals("3"))
		{
			//每3s刷新一次数据
			rg_frequency.check(rb_frequency_every3s.getId());
		}
		else if(State.STATE_FREQUENCY.equals("5"))
		{
			//每5s刷新一次数据
			rg_frequency.check(rb_frequency_every5s.getId());
		}
		
		
		tv_accuracy_remind_custom.setText(getCustomAccuracyText(State.STATE_ACCURACY));
		
	}

	
	private void initView()
	{		
		myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth(); 
	    dm = new DisplayMetrics();  
	    getWindowManager().getDefaultDisplay().getMetrics(dm);  
	    int screenW = dm.widthPixels;
	    
	    DeviceInfor.SCREEN_WIDTH=dm.widthPixels;
	    DeviceInfor.SCREEN_HEIGHT=dm.heightPixels;
	    
		offset = (screenW / 2 - bmpW) / 2;
	    ONETAB = offset * 2 + bmpW;
		matrix = new Matrix();  
	    matrix.postTranslate(offset, 0);  
	    
		mViewPager = (ViewPager)findViewById(R.id.viewpager); 
	    pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab); 
	    pagerTabStrip.setDrawFullUnderline(false); 
	    pagerTabStrip.setTextSpacing(50); 
	    
	    tv_mainpage=(TextView)findViewById(R.id.tv_mainpage);
	    tv_mainpage.setOnClickListener(this);
	    tv_setting=(TextView)findViewById(R.id.tv_setting);
	    tv_setting.setOnClickListener(this);
	    
	    iv_cursor=(ImageView)findViewById(R.id.iv_cursor);
	    iv_cursor.setOnClickListener(this);
	    iv_cursor.setImageMatrix(matrix);
	    
	    
	    layout_main=(LinearLayout)findViewById(R.id.layout_main);
	    layout_title=(RelativeLayout)findViewById(R.id.layout_title);
	    layout_main_divider=(LinearLayout)findViewById(R.id.layout_main_divider);
	    tv_title=(TextView)findViewById(R.id.tv_title);
	    tv_title.setOnClickListener(this);
	    
	    LayoutInflater inflater=LayoutInflater.from(PositionAssistantActivity.this);
	    view1=inflater.inflate(R.layout.item_mainpage, null);
	    view2=inflater.inflate(R.layout.item_setting, null);
	    
	   
	    /******view1******/	 
	    tv_satellite_number=(TextView)view1.findViewById(R.id.tv_satellite_number);
	    tv_satellite_accuracy=(TextView)view1.findViewById(R.id.tv_satellite_accuracy);
	    tv_satellite_altitude=(TextView)view1.findViewById(R.id.tv_satellite_altitude);
	    tv_gps_number=(TextView)view1.findViewById(R.id.tv_gps_number);
	    tv_gps_accuracy=(TextView)view1.findViewById(R.id.tv_gps_accuracy);
	    tv_gps_altitude=(TextView)view1.findViewById(R.id.tv_gps_altitude);   
	    tv_latitude=(TextView)view1.findViewById(R.id.tv_latitude);
	    tv_longitude=(TextView)view1.findViewById(R.id.tv_longitude);
	    et_latitude=(EditText)view1.findViewById(R.id.et_latitude);
	    et_longitude=(EditText)view1.findViewById(R.id.et_longitude);    
	    btn_send_position=(Button)view1.findViewById(R.id.btn_send_position);
	    btn_send_position.setOnClickListener(this);
	    btn_copy_position=(Button)view1.findViewById(R.id.btn_copy_position);
	    btn_copy_position.setOnClickListener(this);
	    btn_copy_to_paster=(Button)view1.findViewById(R.id.btn_copy_to_paste);
	    btn_copy_to_paster.setOnClickListener(this);
	    btn_add_log=(Button)view1.findViewById(R.id.btn_add_log);
	    btn_add_log.setOnClickListener(this);  
	    btn_launch_map=(Button)view1.findViewById(R.id.btn_launch_map);
	    btn_launch_map.setOnClickListener(this);
	    layout_mainpage_divider1=(LinearLayout)view1.findViewById(R.id.layout_mainpage_divider1);
	    layout_mainpage_divider2=(LinearLayout)view1.findViewById(R.id.layout_mainpage_divider2);
	    layout_mainpage_divider3=(LinearLayout)view1.findViewById(R.id.layout_mainpage_divider3);
	    layout_mainpage_divider4=(LinearLayout)view1.findViewById(R.id.layout_mainpage_divider4);
	  
	    /******view1******/
	    
	    
	    
	    /******view2******/	   
	  
	    /******GPS设置******/
	    tv_gps_setting=(TextView)view2.findViewById(R.id.tv_gps_setting);
	    tv_gps_setting.setOnClickListener(this);
	    layout_gps_extend=(LinearLayout)view2.findViewById(R.id.layout_gps_extend);
	    tv_gps=(TextView)view2.findViewById(R.id.tv_gps);
	    layout_gps=(RelativeLayout)view2.findViewById(R.id.layout_gps);
	    layout_gps.setOnClickListener(this);
	    iv_gps=(ImageView)view2.findViewById(R.id.iv_gps);
	    /******GPS设置******/
	     
	    /******联系人******/
	    mmiv_item1=(MyMenuItemView)view2.findViewById(R.id.mmiv_item1);
	    mmiv_item1.setOnClickListener(this);
	    
	    mmiv_item1.setItemTextAndTag("联系人", State.STATE_CONTRACT1, 
	    		                             State.STATE_NUMBER1,
	    		                             State.STATE_CONTRACT2, 
	    		                             State.STATE_NUMBER2, 
	    		                             State.STATE_CONTRACT3, 
	    		                             State.STATE_NUMBER3);
	    /******联系人******/
	       
	    /******主题设置******/
	    tv_theme_setting=(TextView)view2.findViewById(R.id.tv_theme_setting);
	    tv_theme_setting.setOnClickListener(this);
	    layout_theme_extend=(LinearLayout)view2.findViewById(R.id.layout_theme_extend);
	    rg_themesetting=(RadioGroup)view2.findViewById(R.id.rg_themesetting);
	    rb_theme_white=(RadioButton)view2.findViewById(R.id.rb_theme_white);
	    rb_theme_black=(RadioButton)view2.findViewById(R.id.rb_theme_black);
	    /******主题设置******/
	    
	    /******坐标分隔符******/
	    tv_position_divider=(TextView)view2.findViewById(R.id.tv_position_divider);
	    tv_position_divider.setOnClickListener(this);  
	    layout_divider_extend=(LinearLayout)view2.findViewById(R.id.layout_divider_extend);
	    rg_divider=(RadioGroup)view2.findViewById(R.id.rg_divider);
	    rb_divider_space=(RadioButton)view2.findViewById(R.id.rb_divider_space);
	    rb_divider_comma=(RadioButton)view2.findViewById(R.id.rb_divider_comma);
	    rb_divider_semicolon=(RadioButton)view2.findViewById(R.id.rb_divider_semicolon);
	    rb_divider_colon=(RadioButton)view2.findViewById(R.id.rb_divider_colon);
	    /******坐标分隔符******/
	    
	    /******坐标附件信息******/
	    tv_position_plus=(TextView)view2.findViewById(R.id.tv_position_plus);
	    tv_position_plus.setOnClickListener(this);
	    layout_plus_extend=(LinearLayout)view2.findViewById(R.id.layout_plus_extend);
	    cb_plus_send_position=(CheckBox)view2.findViewById(R.id.cb_plus_send_position);
	    cb_plus_copy_position=(CheckBox)view2.findViewById(R.id.cb_plus_copy_position);
	    cb_plus_copy_to_paster=(CheckBox)view2.findViewById(R.id.cb_plus_copy_to_paster);
	    cb_plus_add_log=(CheckBox)view2.findViewById(R.id.cb_plus_add_log);
	    /******坐标附件信息******/
	    
	    /******刷新设置******/
	    tv_refresh_setting=(TextView)view2.findViewById(R.id.tv_refresh_setting);
	    tv_refresh_setting.setOnClickListener(this);
	    layout_refresh_extend=(LinearLayout)view2.findViewById(R.id.layout_refresh_extend);
	    rg_frequency=(RadioGroup)view2.findViewById(R.id.rg_frequency);	  
	    rb_frequency_every1s=(RadioButton)view2.findViewById(R.id.rb_frequency_every1s);
	    rb_frequency_every2s=(RadioButton)view2.findViewById(R.id.rb_frequency_every2s);
	    rb_frequency_every3s=(RadioButton)view2.findViewById(R.id.rb_frequency_every3s);
	    rb_frequency_every5s=(RadioButton)view2.findViewById(R.id.rb_frequency_every5s);
	    /******刷新设置******/
	    
	    
	    /******精度提醒******/
	    tv_accuracy_remind = (TextView)view2.findViewById(R.id.tv_accuracy_remind);
	    tv_accuracy_remind.setOnClickListener(this);
	    layout_accuracy_extend = (LinearLayout)view2.findViewById(R.id.layout_accuracy_extend);
	    cb_accuracy_remind = (CheckBox)view2.findViewById(R.id.cb_accuracy_remind);
	    tv_accuracy_remind_custom = (TextView)view2.findViewById(R.id.tv_accuracy_remind_custom);
	    layout_accuracy_remind_custom = (RelativeLayout)view2.findViewById(R.id.layout_accuracy_remind_custom);
	    layout_accuracy_remind_custom.setOnClickListener(this);
	    /******精度提醒******/
	    
	    /******短信记录******/
	    tv_sms_record     = (TextView)view2.findViewById(R.id.tv_sms_record);
	    tv_sms_record.setOnClickListener(this);
	    layout_sms_extend = (LinearLayout)view2.findViewById(R.id.layout_sms_extend);	    
	    layout_sms_record10 = (RelativeLayout)view2.findViewById(R.id.layout_sms_record10);
	    layout_sms_record10.setOnClickListener(this);
	    tv_sms_record10   = (TextView)view2.findViewById(R.id.tv_sms_record10);
	    //tv_sms_record10.setOnClickListener(this);
	    /******短信记录******/
	    
	    tv_copyright=(TextView)view2.findViewById(R.id.tv_copyright);
	    
	    layout_setting_divider1=(LinearLayout)view2.findViewById(R.id.layout_setting_divider1);
	    layout_setting_divider2=(LinearLayout)view2.findViewById(R.id.layout_setting_divider2);
	    layout_setting_divider3=(LinearLayout)view2.findViewById(R.id.layout_setting_divider3);
	    layout_setting_divider4=(LinearLayout)view2.findViewById(R.id.layout_setting_divider4);
	    layout_setting_divider5=(LinearLayout)view2.findViewById(R.id.layout_setting_divider5);
	    layout_setting_divider6=(LinearLayout)view2.findViewById(R.id.layout_setting_divider6);
	    layout_setting_divider7=(LinearLayout)view2.findViewById(R.id.layout_setting_divider7);
	    
	    layout_setting_divider8=(LinearLayout)view2.findViewById(R.id.layout_setting_divider8);
	    layout_setting_divider9=(LinearLayout)view2.findViewById(R.id.layout_setting_divider9);
	    layout_setting_divider10=(LinearLayout)view2.findViewById(R.id.layout_setting_divider10);
	    
	    layout_setting_divider11=(LinearLayout)view2.findViewById(R.id.layout_setting_divider11);
	    layout_setting_divider12=(LinearLayout)view2.findViewById(R.id.layout_setting_divider12);
	    layout_setting_divider13=(LinearLayout)view2.findViewById(R.id.layout_setting_divider13);
	    layout_setting_divider14=(LinearLayout)view2.findViewById(R.id.layout_setting_divider14);
	    layout_setting_divider15=(LinearLayout)view2.findViewById(R.id.layout_setting_divider15);
	    layout_setting_divider16=(LinearLayout)view2.findViewById(R.id.layout_setting_divider16);
	    layout_setting_divider17=(LinearLayout)view2.findViewById(R.id.layout_setting_divider17);
	    layout_setting_divider18=(LinearLayout)view2.findViewById(R.id.layout_setting_divider18);
	    layout_setting_divider19=(LinearLayout)view2.findViewById(R.id.layout_setting_divider19);
	    layout_setting_divider20=(LinearLayout)view2.findViewById(R.id.layout_setting_divider20);
	   
	    btn_save_setting=(Button)view2.findViewById(R.id.btn_save_setting);
	    btn_save_setting.setOnClickListener(this);
	    btn_exit=(Button)view2.findViewById(R.id.btn_exit);
	    btn_exit.setOnClickListener(this);  
	    
	    setExtend(isThemeExtend,tv_theme_setting, layout_theme_extend);
	    setExtend(isDividerExtend,tv_position_divider,layout_divider_extend);
	    setExtend(isPlusExtend,tv_position_plus,layout_plus_extend);
	    setExtend(isRefreshExtend,tv_refresh_setting,layout_refresh_extend);
	    setExtend(isGPSExtend,tv_gps_setting, layout_gps_extend);
	    setExtend(isAccuracyExtend,tv_accuracy_remind,layout_accuracy_extend);
	    setExtend(isSMSRecordExtend,tv_sms_record,layout_sms_extend);
	    /******view2******/
	    
	    mListViews = new ArrayList<View>();
        mListViews.add(view1);  
        mListViews.add(view2);  
       
  
        mTitleList = new ArrayList<String>();
        mTitleList.add("主界面");  
        mTitleList.add("设置");  
        
        adapter=new MyViewPagerAdapter(mListViews, mTitleList);
        
        mViewPager.setAdapter(adapter);
        
        setCurrentItem(0);
	    
	    
	    dialog_add_log = new MyDialogAddLog(PositionAssistantActivity.this,new OnClickListener()
	    {
			@Override
			public void onClick(View v)
			{
				
				if(v.getId()==R.id.tv_dialog_add_log_ok)
				{
					if((!et_latitude.getText().toString().equals(""))&&(!et_longitude.getText().toString().equals("")))
					{
						String place = dialog_add_log.getPlaceText();
						String remark = dialog_add_log.getRemarkText();
						
					    //记录坐标时，可以不输入【地点】以及【备注信息】
						//if(!((place.equals(""))&&(remark.equals(""))))
						//{
							try
							{
							 
							    CSVWriter.fileExist(Consts.file_log);					
							    CSVWriter.writeText(getLogString("记录当前坐标到日志",place,remark));
							    InforToast.showInfor(PositionAssistantActivity.this, "已记录当前坐标",State.STATE_THEME);
							}
							catch(Exception ex)
							{
								 InforToast.showInfor(PositionAssistantActivity.this, ex.toString(),State.STATE_THEME);
							}
						//}
					}
					else
					{
						InforToast.showInfor(PositionAssistantActivity.this,  "定位尚未成功，请稍后重试", State.STATE_THEME);
					}				
						
				}
				
				if(v.getId()==R.id.tv_dialog_add_log_cancel)
				{
					
				}
			   
				dialog_add_log.dismiss();
			}
	    	
	    });
	    
   
	    dialog_contract1 = new MyDialogContract(PositionAssistantActivity.this, new OnClickListener()
	    {

			@Override
			public void onClick(View v) 
			{
				if(v.getId()==R.id.tv_dialog_contract_ok)
				{
					//数值验证
					State.STATE_CONTRACT1 = dialog_contract1.getNameText();
					State.STATE_NUMBER1 =dialog_contract1.getNumberText();
					
					if(State.STATE_CONTRACT1.equals(""))
					{
						State.STATE_CONTRACT1="联系人1";
					}
					
					if(State.STATE_NUMBER1.equals(""))
					{
						State.STATE_NUMBER1="新建联系人";
					}
					
					dialog_contract1.setInfor(State.STATE_CONTRACT1,State.STATE_NUMBER1);
					
					mmiv_item1.setItemText1(State.STATE_CONTRACT1);
					mmiv_item1.setItemText11(State.STATE_NUMBER1);
					
					saveSetting();	
					
				}
				
				if(v.getId()==R.id.tv_dialog_contract_cancel)
				{
					
				}
				
				dialog_contract1.dismiss();
				
			}
	    	
	    }, State.STATE_CONTRACT1, State.STATE_NUMBER1);
	    
	    
	    dialog_contract2 = new MyDialogContract(PositionAssistantActivity.this, new OnClickListener()
	    {

			@Override
			public void onClick(View v) 
			{
				if(v.getId()==R.id.tv_dialog_contract_ok)
				{
					//数值验证
					State.STATE_CONTRACT2 = dialog_contract2.getNameText();
					State.STATE_NUMBER2 =dialog_contract2.getNumberText();
					
					if(State.STATE_CONTRACT2.equals(""))
					{
						State.STATE_CONTRACT2="联系人2";
					}
					
					if(State.STATE_NUMBER2.equals(""))
					{
						State.STATE_NUMBER2="新建联系人";
					}
					
					dialog_contract2.setInfor(State.STATE_CONTRACT2,State.STATE_NUMBER2);
					
					mmiv_item1.setItemText2(State.STATE_CONTRACT2);
					mmiv_item1.setItemText22(State.STATE_NUMBER2);
					
					saveSetting();	
				}
				
				if(v.getId()==R.id.tv_dialog_contract_cancel)
				{
					
				}
				
				dialog_contract2.dismiss();
				
			}
	    	
	    }, State.STATE_CONTRACT2, State.STATE_NUMBER2);
	    
	    
	    dialog_contract3 = new MyDialogContract(PositionAssistantActivity.this, new OnClickListener()
	    {

			@Override
			public void onClick(View v) 
			{
				
				if(v.getId()==R.id.tv_dialog_contract_ok)
				{
					//数值验证
					State.STATE_CONTRACT3 = dialog_contract3.getNameText();
					State.STATE_NUMBER3 =dialog_contract3.getNumberText();
					
					if(State.STATE_CONTRACT3.equals(""))
					{
						State.STATE_CONTRACT3="联系人3";
					}
					
					if(State.STATE_NUMBER3.equals(""))
					{
						State.STATE_NUMBER3="新建联系人";
					}
					
					dialog_contract3.setInfor(State.STATE_CONTRACT3,State.STATE_NUMBER3);
					
					mmiv_item1.setItemText3(State.STATE_CONTRACT3);
					mmiv_item1.setItemText33(State.STATE_NUMBER3);
					
					saveSetting();	
				}
				
				if(v.getId()==R.id.tv_dialog_contract_cancel)
				{
					
				}
				
				dialog_contract3.dismiss();
				
			}
	    	
	    }, State.STATE_CONTRACT3, State.STATE_NUMBER3);
	    
	 
	    
	    dialog_accuracy = new MyDialogAccuracy(PositionAssistantActivity.this, new OnClickListener() 
	    {
			
			@Override
			public void onClick(View v) 
			{
				
				if(v.getId() == R.id.tv_dialog_accuracy_ok)
				{
					
					
					//数值验证
					if(!dialog_accuracy.getText().equals(""))
					{
						double number = Double.valueOf(dialog_accuracy.getText());
						number = FormatManager.getAltitudeFormat(number);					
						if(number>=0.1)
						{	
						    State.STATE_ACCURACY = String.valueOf(number);
						
						    dialog_accuracy.setInfor(State.STATE_ACCURACY);
						   
						    tv_accuracy_remind_custom.setText(getCustomAccuracyText(State.STATE_ACCURACY));			
						  
						    try
							{
								save();																										
							}
							catch(Exception ex)
							{
								InforToast.showInfor(PositionAssistantActivity.this, ex.toString(), State.STATE_THEME);
							}	    
	    
						}
						else
						{	
							InforToast.showInfor(PositionAssistantActivity.this, "温馨提示：提醒精度不能小于1m", State.STATE_THEME);			
						}
					}								
				
				}
				
				
				if(v.getId() == R.id.tv_dialog_accuracy_cancel);
				{
					
				}
				
			    dialog_accuracy.dismiss();
			   
			}
		}, State.STATE_ACCURACY);
	    
	    
	    
	    
	        
	    rg_themesetting.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				if(checkedId==rb_theme_black.getId())
				{
					//炫酷黑
					State.STATE_THEME="1";	
					setMyTheme(State.STATE_THEME);
					
					
				}
				else
				{
					//简约白
					State.STATE_THEME="0";
					setMyTheme(State.STATE_THEME);
				}
				
				save();
				
			}
		});
	    
	    
	    rg_divider.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				if(checkedId==rb_divider_comma.getId())
				{
					//逗号
					State.STATE_DIVIDER="1";
				}
				else if(checkedId==rb_divider_semicolon.getId())
				{
					//分号
					State.STATE_DIVIDER="2";
				}
				else if(checkedId==rb_divider_colon.getId())
				{
					//冒号
					State.STATE_DIVIDER="3";
				}
				else
				{
					//(双)空格
					State.STATE_DIVIDER="0";
				}
				
				save();
					
			}
		});
	    
	    
	    rg_frequency.setOnCheckedChangeListener(new OnCheckedChangeListener()
	    {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				if(checkedId==rb_frequency_every1s.getId())
				{
					State.STATE_FREQUENCY="1";
				}
				else if(checkedId==rb_frequency_every2s.getId())
				{
					State.STATE_FREQUENCY="2";
				}
				else if(checkedId==rb_frequency_every3s.getId())
				{
					State.STATE_FREQUENCY="3";
				}
				else if(checkedId==rb_frequency_every5s.getId())
				{
					State.STATE_FREQUENCY="5";
				}
				else
				{
					State.STATE_FREQUENCY="0";
				}
				
				try
				{
					save();
					
					String result = "1";			
					result=State.STATE_FREQUENCY;
					setPositionProvider(Double.valueOf(result));
					
				}
				catch(Exception ex)
				{
					InforToast.showInfor(PositionAssistantActivity.this, ex.toString(), State.STATE_THEME);
				}			
			}			
		});
	    
	
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() 
        {
			
			@Override
			public void onPageSelected(int position)
			{
				 Animation animation = new TranslateAnimation(ONETAB*currIndex, ONETAB*position, 0, 0); 	 
			     currIndex = position;  
				 animation.setFillAfter(true);
				 animation.setDuration(300);  
				 iv_cursor.startAnimation(animation); 
				 setCurrentItem(position);
			}
			
			@Override
			public void onPageScrolled(int position, float arg1, int arg2)
			{
				
			}
			
			@Override
			public void onPageScrollStateChanged(int position) 
			{
				
			}
		});
	    
	}
	
	
	private boolean isBetterAccuracy(String accuracy)
	{
		boolean result       = false;
		
		double  std_accuracy = Double.valueOf(accuracy);
		
		if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
		{
		    if(GPSLocation.GPS_LOCATION_ACCURACY_D <= std_accuracy)
		    {
			    result = true;
		    }
		}

		
		
		return result;
	}
	
	
	private void save()
	{
		DatabaseManager.writeDataToSettingFile(PositionAssistantActivity.this,State.STATE_THEME,State.STATE_DIVIDER,
                                                                              State.STATE_PLUS_SP,State.STATE_PLUS_CS,
                                                                              State.STATE_PLUS_CP,State.STATE_PLUS_RL,
                                                                              State.STATE_FREQUENCY,
                                                                              State.STATE_CONTRACT1,State.STATE_NUMBER1,
                                                                              State.STATE_CONTRACT2,State.STATE_NUMBER2,
                                                                              State.STATE_CONTRACT3,State.STATE_NUMBER3,
                                                                              State.STATE_ACCURACY_REMIND,State.STATE_ACCURACY);
	}
	
	
	private void saveSetting()
	{		
		getMyTheme();
		getDivider();
		getFrequency();
		setMyTheme(State.STATE_THEME);
		save();
		try
		{
			double temp = Double.valueOf(getCurrentFrequency());
		    setPositionProvider(temp);	
		}
		catch(Exception ex)
		{
			InforToast.showInfor(PositionAssistantActivity.this, ex.toString(), State.STATE_THEME);
		}				
		
	}
	
	
	
	private void setCheckBox(CheckBox cb_plus,String STATE_PLUS)
	{
		if(STATE_PLUS.equals("1"))
		{
			cb_plus.setChecked(true);
		}
		else
		{
			cb_plus.setChecked(false);
		}
	}
	
	
	private void setCollapseAll()
	{
		isGPSExtend        = false;
		mmiv_item1.setExtend(false);
		isThemeExtend      = false;
		isDividerExtend    = false;
		isPlusExtend       = false;
		isRefreshExtend    = false;
		isAccuracyExtend   = false;
		isSMSRecordExtend  = false;
		
		setExtend(isGPSExtend, tv_gps_setting, layout_gps_extend);
		setExtend(isThemeExtend, tv_theme_setting, layout_theme_extend);
		setExtend(isDividerExtend, tv_position_divider, layout_divider_extend);
		setExtend(isPlusExtend, tv_position_plus, layout_plus_extend);
		setExtend(isRefreshExtend, tv_refresh_setting, layout_refresh_extend);
		setExtend(isAccuracyExtend, tv_accuracy_remind, layout_accuracy_extend);
		setExtend(isSMSRecordExtend, tv_sms_record, layout_sms_extend);
		
	}
	
	
	private void setCurrentItem(int position)
	{
		if(position==1)
		{
			mViewPager.setCurrentItem(1);			
			
			if(State.STATE_THEME.equals("1"))
			{
				tv_mainpage.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			}
			else
			{
				tv_mainpage.setTextColor(Color.rgb(0,0,0));
			}
						
		
			tv_setting.setTextColor(Consts.COLOR_STYLE_PUBLIC_GREEN);
		}
		
		else
		{
			mViewPager.setCurrentItem(0);
			tv_mainpage.setTextColor(Consts.COLOR_STYLE_PUBLIC_GREEN);
			
			if(State.STATE_THEME.equals("1"))
			{
				tv_setting.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			}
			else
			{
				tv_setting.setTextColor(Color.rgb(0,0,0));
			}
					
		}
	}

	
	private void setExtend(boolean isExtend,TextView tv_title_menu,LinearLayout layout_extend)
	{
		
		if(isExtend)
		{
			//展开
			LinearLayout.LayoutParams params =(LinearLayout.LayoutParams) layout_extend.getLayoutParams();
			//params.width= 240;
			params.height=LinearLayout.LayoutParams.WRAP_CONTENT;
			//params.height=150;
			layout_extend.setLayoutParams(params);
			Drawable drawable = getResources().getDrawable(R.drawable.arrextend_b);
			drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tv_title_menu.setCompoundDrawables(drawable, null, null, null);
		}
		else
		{
			//折叠
			LinearLayout.LayoutParams params =(LinearLayout.LayoutParams) layout_extend.getLayoutParams();
			//params.width= 240;
			params.height=0;
			layout_extend.setLayoutParams(params);
			Drawable drawable = getResources().getDrawable(R.drawable.arrextend_a);
			drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tv_title_menu.setCompoundDrawables(drawable, null, null, null);
		}
	}
	
	
	private void setExpandAll()
	{
		isGPSExtend        = true;
		mmiv_item1.setExtend(true);
		isThemeExtend      = true;
		isDividerExtend    = true;
		isPlusExtend       = true;
		isRefreshExtend    = true;
		isAccuracyExtend   = true;
		isSMSRecordExtend  = true;
		
		setExtend(isGPSExtend, tv_gps_setting, layout_gps_extend);
		setExtend(isThemeExtend, tv_theme_setting, layout_theme_extend);
		setExtend(isDividerExtend, tv_position_divider, layout_divider_extend);
		setExtend(isPlusExtend, tv_position_plus, layout_plus_extend);
		setExtend(isRefreshExtend, tv_refresh_setting, layout_refresh_extend);
		setExtend(isAccuracyExtend, tv_accuracy_remind, layout_accuracy_extend);
		setExtend(isSMSRecordExtend, tv_sms_record, layout_sms_extend);
		
	}
	
	
	private void setMyTheme(String THEME)
	{
		if(THEME.equals("1"))
		{
			
			//炫酷黑
			layout_main.setBackgroundResource(R.drawable.theme_black);
			layout_title.setBackgroundResource(R.drawable.titlebar_black);
			tv_title.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			
			if(mViewPager.getCurrentItem()==1)
			{
				tv_mainpage.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
				tv_setting.setTextColor(Consts.COLOR_STYLE_PUBLIC_GREEN);
			}
			else
			{
				tv_mainpage.setTextColor(Consts.COLOR_STYLE_PUBLIC_GREEN);
				tv_setting.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			}
			
			 tv_satellite_number.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 tv_satellite_accuracy.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 tv_satellite_altitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 tv_latitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 tv_longitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT); 
			 et_latitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 et_longitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 
			 tv_gps.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 tv_accuracy_remind_custom.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 tv_sms_record10.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 
			 rb_theme_white.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_theme_black.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_divider_space.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_divider_comma.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_divider_semicolon.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_divider_colon.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_frequency_every1s.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_frequency_every2s.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_frequency_every3s.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 rb_frequency_every5s.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			
			 layout_main_divider.setBackgroundResource(R.drawable.divider_black);
			 
			 layout_mainpage_divider1.setBackgroundResource(R.drawable.divider_cb);
			 layout_mainpage_divider2.setBackgroundResource(R.drawable.divider_cb);
			 layout_mainpage_divider3.setBackgroundResource(R.drawable.divider_black);
			 layout_mainpage_divider4.setBackgroundResource(R.drawable.divider_black);
			 
			 layout_setting_divider1.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider2.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider3.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider4.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider5.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider6.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider7.setBackgroundResource(R.drawable.divider_black);
			 
			 layout_setting_divider8.setBackgroundResource(R.drawable.divider_cb);
			 layout_setting_divider9.setBackgroundResource(R.drawable.divider_cb);
			 layout_setting_divider10.setBackgroundResource(R.drawable.divider_cb);
			 
			 layout_setting_divider11.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider12.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider13.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider14.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider15.setBackgroundResource(R.drawable.divider_black);	 
			 layout_setting_divider16.setBackgroundResource(R.drawable.divider_black);
			 
			 layout_setting_divider15.setBackgroundResource(R.drawable.divider_cb);
			 
			 layout_setting_divider18.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider19.setBackgroundResource(R.drawable.divider_black);
			 layout_setting_divider20.setBackgroundResource(R.drawable.divider_black);
			
			 
			 cb_plus_send_position.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 cb_plus_copy_position.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 cb_plus_copy_to_paster.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 cb_plus_add_log.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 cb_accuracy_remind.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			 
			 
			 
			 rg_themesetting.setDividerDrawable(getBaseContext().getResources().getDrawable(R.drawable.divider_cb));
			 rg_divider.setDividerDrawable(getBaseContext().getResources().getDrawable(R.drawable.divider_cb));
			 rg_frequency.setDividerDrawable(getBaseContext().getResources().getDrawable(R.drawable.divider_cb));
			 
			 //tv_use_outdoor.setTextColor(Consts.COLOR_THEME_BLACK_TEXT2);
			 tv_copyright.setTextColor(Consts.COLOR_THEME_BLACK_TEXT2);
		}
		else
		{			
			//简约白
			layout_main.setBackgroundResource(R.drawable.theme_default);
			layout_title.setBackgroundResource(R.drawable.titlebar_default);
			tv_title.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT_TITLE);
	        
			
			if(mViewPager.getCurrentItem()==1)
			{
				tv_mainpage.setTextColor(Color.BLACK);
				tv_setting.setTextColor(Consts.COLOR_STYLE_PUBLIC_GREEN);
			}
			else
			{
				tv_mainpage.setTextColor(Consts.COLOR_STYLE_PUBLIC_GREEN);
				tv_setting.setTextColor(Color.BLACK);
			}
			
			
			 tv_satellite_number.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 tv_satellite_accuracy.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 tv_satellite_altitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 tv_latitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 tv_longitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 et_latitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 et_longitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 
			 tv_gps.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 tv_accuracy_remind_custom.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 tv_sms_record10.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 
			 rb_theme_white.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_theme_black.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_divider_space.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_divider_comma.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_divider_semicolon.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_divider_colon.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_frequency_every1s.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_frequency_every2s.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_frequency_every3s.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 rb_frequency_every5s.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);

			
			 layout_main_divider.setBackgroundResource(R.drawable.divider_default);
			 layout_mainpage_divider1.setBackgroundResource(R.drawable.divider_default);
			 layout_mainpage_divider2.setBackgroundResource(R.drawable.divider_default);
			 layout_mainpage_divider3.setBackgroundResource(R.drawable.divider_default);
			 layout_mainpage_divider4.setBackgroundResource(R.drawable.divider_default);
			
			 layout_setting_divider1.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider2.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider3.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider4.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider5.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider6.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider7.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider8.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider9.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider10.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider11.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider12.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider13.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider14.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider15.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider16.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider17.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider18.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider19.setBackgroundResource(R.drawable.divider_default);
			 layout_setting_divider20.setBackgroundResource(R.drawable.divider_default);
			
			 			 
			 cb_plus_send_position.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 cb_plus_copy_position.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 cb_plus_copy_to_paster.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 cb_plus_add_log.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 cb_accuracy_remind.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			 
			 
			 rg_themesetting.setDividerDrawable(getBaseContext().getResources().getDrawable(R.drawable.divider_default));
			 rg_divider.setDividerDrawable(getBaseContext().getResources().getDrawable(R.drawable.divider_default));
			 rg_frequency.setDividerDrawable(getBaseContext().getResources().getDrawable(R.drawable.divider_default));
			
			 
			 //tv_use_outdoor.setTextColor(Consts.COLOR_THEME_BLACK_TEXT2);
			 tv_copyright.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT2);
			 
		}
		
		mmiv_item1.setMMIVTheme(State.STATE_THEME);
		
		
		
	}
	

	private void setPositionProvider(double time) 
	{		
		
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
        	  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (int)(time*1000), 0, this); 
        }
        else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
        	  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, (int)(time*1000), 0, this);
        }	
        else
        {
        	
        }
	}
	
	
	private void updateView(Location location)
	{
		if(location!=null)
		{
			
			GPSLocation.GPS_LOCATION_ACCURACY_D  = FormatManager.getAccuracyFormat(location.getAccuracy());
			GPSLocation.GPS_LOCATION_ALTITUDE_D  = FormatManager.getAltitudeFormat(location.getAltitude());		
			GPSLocation.GPS_LOCATION_LATITUDE_D  = FormatManager.getPositionFormat(location.getLatitude());
			GPSLocation.GPS_LOCATION_LONGITUDE_D = FormatManager.getPositionFormat(location.getLongitude());
			
			tv_gps_accuracy.setText(String.valueOf(FormatManager.getAccuracyFormat(location.getAccuracy())));
			tv_gps_altitude.setText(String.valueOf(FormatManager.getAltitudeFormat(location.getAltitude())));
			et_latitude.setText(String.valueOf(FormatManager.getPositionFormat(location.getLatitude())));
			et_longitude.setText(String.valueOf(FormatManager.getPositionFormat(location.getLongitude())));	
			
			if(GpsManager.isGPSEnable(PositionAssistantActivity.this))
			{
				//使用默认主题颜色
				if(State.STATE_THEME.equals("1"))
				{
					//炫酷黑主题
					if((State.STATE_ACCURACY_REMIND.equals("1"))&&(!isBetterAccuracy(State.STATE_ACCURACY)))
					{
						et_latitude.setTextColor(Color.RED);
						et_longitude.setTextColor(Color.RED);
					}
					else
				    {
						et_latitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
						et_longitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
					}
					 
		
				}
				else
				{
					//简约白主题
					if((State.STATE_ACCURACY_REMIND.equals("1"))&&(!isBetterAccuracy(State.STATE_ACCURACY)))
					{
						et_latitude.setTextColor(Color.RED);
						et_longitude.setTextColor(Color.RED);
					}
					else
				    {
						et_latitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
						et_longitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
					}
				}
					
			}
			else
			{
				//使用默认主题颜色
				if(State.STATE_THEME.equals("1"))
				{
					//炫酷黑主题
					et_latitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
					et_longitude.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
				}
				else
				{
					//简约白主题
					et_latitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
					et_longitude.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
				}
			}
			
			
			
		}
		else
		{
			tv_gps_accuracy.setText("0");
			et_latitude.setText("");
			et_longitude.setText("");			
		}		
		
	}
	
	
	@Override
	public void onClick(View view) 
	{
		if(view.getId() == tv_mainpage.getId())
		{
			setCurrentItem(0);
		}
		
		if(view.getId() == tv_setting.getId())
		{
			setCurrentItem(1);
		}
		
		//一键短信发送坐标	
		if(view.getId() == btn_send_position.getId())
		{					
			
			if((!et_latitude.getText().toString().equals(""))&&(!et_longitude.getText().toString().equals("")))
			{			

				String message = getMainMessage1();	
				copy(message);
				
			    boolean isNumber1 = MobileNumberManager.isMobileNO(State.STATE_NUMBER1);
			    boolean isNumber2 = MobileNumberManager.isMobileNO(State.STATE_NUMBER2);
			    boolean isNumber3 = MobileNumberManager.isMobileNO(State.STATE_NUMBER3);
			   	
			    String appendix = "";
			    
			    if(canSendMessage(isNumber1, isNumber2, isNumber3))
			    {
			    	
			    	if(isNumber1)
			    	{
			    		MessageSender.sendMessage1(PositionAssistantActivity.this, State.STATE_NUMBER1, message,FormatManager.getContract(State.STATE_CONTRACT1, 1),State.STATE_NUMBER1);
			    		DatabaseManager.writerDataToSMSFile(PositionAssistantActivity.this,Launch.getSystemDate(), Launch.getSystemTime(), State.STATE_CONTRACT1, State.STATE_NUMBER1, message);
			    		initSMSInforFile();
			    		appendix=appendix+","+State.STATE_CONTRACT1+"("+State.STATE_NUMBER1+")";
			    	}
			    	
			    	if(isNumber2)
			    	{
			    		MessageSender.sendMessage2(PositionAssistantActivity.this, State.STATE_NUMBER2, message,FormatManager.getContract(State.STATE_CONTRACT2, 2),State.STATE_NUMBER2);
			    		DatabaseManager.writerDataToSMSFile(PositionAssistantActivity.this,Launch.getSystemDate(), Launch.getSystemTime(), State.STATE_CONTRACT2, State.STATE_NUMBER2, message);
			    		initSMSInforFile();
			    		appendix=appendix+","+State.STATE_CONTRACT2+"("+State.STATE_NUMBER2+")";
			    	}
			    	
			    	if(isNumber3)
			    	{
			    		MessageSender.sendMessage3(PositionAssistantActivity.this, State.STATE_NUMBER3, message,FormatManager.getContract(State.STATE_CONTRACT3, 3),State.STATE_NUMBER3);
			    		DatabaseManager.writerDataToSMSFile(PositionAssistantActivity.this,Launch.getSystemDate(), Launch.getSystemTime(), State.STATE_CONTRACT3, State.STATE_NUMBER3, message);
			    		initSMSInforFile();
			    		appendix=appendix+","+State.STATE_CONTRACT3+"("+State.STATE_NUMBER3+")";
			    	}			    	
			    	
			    	CSVWriter.fileExist(Consts.file_log);					
					CSVWriter.writeText(getLogString("一键短信发送坐标")+appendix);
			    	
			    }
			    else
			    {	
			    	InforToast.showInfor(PositionAssistantActivity.this, "温馨提示:请先设置联系人",State.STATE_THEME);
			    }	
			}
			else
			{
		    	InforToast.showInfor(PositionAssistantActivity.this, "定位尚未成功，请稍后重试",State.STATE_THEME);
			}
			    
		}
		
		
		//复制并分享坐标
		if(view.getId() == btn_copy_position.getId())
		{			
			
				if((!et_latitude.getText().toString().equals(""))&&(!et_longitude.getText().toString().equals("")))
				{
					String message = getMainMessage2();					
					copy(message);
					CSVWriter.fileExist(Consts.file_log);					
					CSVWriter.writeText(getLogString("复制并分享坐标"));				
					
					if(State.STATE_THEME.equals("1"))
					{
						my_share_window2 = new MyShareWindow2(PositionAssistantActivity.this, this);          
						//设置layout在PopupWindow中显示的位置  
						my_share_window2.showAtLocation(PositionAssistantActivity.this.findViewById(R.id.et_longitude), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
					}
					else
					{
						my_share_window = new MyShareWindow(PositionAssistantActivity.this, this);          
						//设置layout在PopupWindow中显示的位置  
						my_share_window.showAtLocation(PositionAssistantActivity.this.findViewById(R.id.et_longitude), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
					}						
				}
				else
				{
					InforToast.showInfor(PositionAssistantActivity.this,  "定位尚未成功，请稍后重试", State.STATE_THEME);
				}
			
		}
		
		
		//复制坐标到剪贴板
		if(view.getId() == btn_copy_to_paster.getId())
		{					
		
				if((!et_latitude.getText().toString().equals(""))&&(!et_longitude.getText().toString().equals("")))
				{
					String message = getMainMessage3();					
					copy(message);
					CSVWriter.fileExist(Consts.file_log);					
					CSVWriter.writeText(getLogString("复制坐标到剪贴板"));				
					InforToast.showInfor(PositionAssistantActivity.this,  "已复制当前坐标", State.STATE_THEME);
				}
				else
				{
					InforToast.showInfor(PositionAssistantActivity.this,  "定位尚未成功，请稍后重试", State.STATE_THEME);
				}
		}	
		
		//记录当前坐标到日志
		if(view.getId() == btn_add_log.getId())
		{			
			try
			{
			
			    if((!et_latitude.getText().toString().equals(""))&&(!et_longitude.getText().toString().equals("")))
			    {
			    	GPSLocation.GPS_LOCATION_LATITUDE  = et_latitude.getText().toString();
					GPSLocation.GPS_LOCATION_LONGITUDE = et_longitude.getText().toString();
					GPSLocation.GPS_LOCATION_ALTITUDE  = tv_gps_altitude.getText().toString();
					GPSLocation.GPS_LOCATION_ACCURACY  = tv_gps_accuracy.getText().toString();
			    	
				
					String message = getMainMessage4();
					copy(message);
					
				    dialog_add_log.show();
				    dialog_add_log.setDialogTheme(State.STATE_THEME);
				    				   
				    
				    Timer timer = new Timer();  
				    timer.schedule(new TimerTask() 
				    {  				  
				        @Override  
				        public void run() 
				        {  
				    	    dialog_add_log.showKeyboard();  
				        }  
				    }, 200); //延时200ms
				      
			    }
			    else
			    {
			    	InforToast.showInfor(PositionAssistantActivity.this,  "定位尚未成功，请稍后重试", State.STATE_THEME);
			    }
			}
			catch(Exception ex)
			{
				InforToast.showInfor(PositionAssistantActivity.this, ex.toString(), State.STATE_THEME);
			}
		}
		
		//启动地图
		if(view.getId() == btn_launch_map.getId())
		{
			//启动AMap
			
			Intent intent = new Intent();		
			intent.setClass(PositionAssistantActivity.this, AMapActivity.class);
			startActivity(intent);

			//AnimationManagerSystem.fromSlide(PositionAssistantActivity.this);
			AnimationManagerSystem.fromFade(PositionAssistantActivity.this);
			
		}
		
		if((view.getId()==R.id.layout_share_wechat))
		{
			//调用微信
			try
			{		
				my_share_window.dismiss();
			    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");  
			    startActivity(intent); 
			}
			catch(Exception e)
			{
				InforToast.showInfor(PositionAssistantActivity.this,  "温馨提示：没有找到本地应用", State.STATE_THEME);
			}
		}
		
		if((view.getId()==R.id.layout_share_wechat2))
		{
			//调用微信
			try
			{
				my_share_window2.dismiss();
			    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");  
			    startActivity(intent); 
			}
			catch(Exception e)
			{
				InforToast.showInfor(PositionAssistantActivity.this,  "温馨提示：没有找到本地应用", State.STATE_THEME);
			}
		}
		
		
		if((view.getId()==R.id.layout_share_message))
		{
			//调用短信			
			try
			{
				my_share_window.dismiss();
				
				String message = getMainMessage2();					
				
				Intent intent = new Intent(Intent.ACTION_VIEW);  
				intent.setType("vnd.android-dir/mms-sms"); 
				intent.putExtra("sms_body",message);    
				startActivity(intent);  
			}
			catch(Exception e)
			{
				InforToast.showInfor(PositionAssistantActivity.this,  "温馨提示：没有找到本地应用", State.STATE_THEME);
			}
				
		}
		
		
		if((view.getId()==R.id.layout_share_message2))
		{
			//调用短信			
			try
			{
				my_share_window2.dismiss();
				
				String message = getMainMessage2();		
				
				Intent intent = new Intent(Intent.ACTION_VIEW);  
				intent.setType("vnd.android-dir/mms-sms"); 
				intent.putExtra("sms_body",message);    
				startActivity(intent);  
			}
			catch(Exception e)
			{
				InforToast.showInfor(PositionAssistantActivity.this,  "温馨提示：没有找到本地应用", State.STATE_THEME);
			}
				
		}
		
		
		
		if(view.getTag()==mmiv_item1.getItemTitle())
		{
			mmiv_item1.setExtend(mmiv_item1.getExtend());
		}
		
		
		if(view.getId() == tv_gps_setting.getId())
		{
			isGPSExtend=!isGPSExtend;
			setExtend(isGPSExtend, tv_gps_setting, layout_gps_extend);
		}
		
		
		if(view.getId() == tv_theme_setting.getId())
		{
			isThemeExtend=!isThemeExtend;
			setExtend(isThemeExtend, tv_theme_setting, layout_theme_extend);
		}
		
		if(view.getId() == tv_position_divider.getId())
		{
			isDividerExtend=!isDividerExtend;
			setExtend(isDividerExtend, tv_position_divider, layout_divider_extend);
		}
		
		if(view.getId() == tv_position_plus.getId())
		{
			isPlusExtend=!isPlusExtend;
			setExtend(isPlusExtend, tv_position_plus, layout_plus_extend);
		}
		
		if(view.getId() == tv_refresh_setting.getId())
		{
			isRefreshExtend=!isRefreshExtend;
			setExtend(isRefreshExtend, tv_refresh_setting, layout_refresh_extend);
		}
		
	
		
		if(view.getId() == tv_accuracy_remind.getId())
		{
			isAccuracyExtend = !isAccuracyExtend;
			setExtend(isAccuracyExtend, tv_accuracy_remind, layout_accuracy_extend);
		}
		
		if(view.getId() == tv_sms_record.getId())
		{
			isSMSRecordExtend = !isSMSRecordExtend;
			setExtend(isSMSRecordExtend, tv_sms_record, layout_sms_extend);
		}
		
		if(view.getId() == R.id.layout_sms_record10)
		{
			Intent intent = new Intent();		
			intent.setClass(PositionAssistantActivity.this, SMSRecordActivity.class);
			startActivity(intent);
			AnimationManagerSystem.fromFade(PositionAssistantActivity.this);
		}
		
		
		if(view.getId() == tv_title.getId())
		{
			if(mViewPager.getCurrentItem()==1)
			{
			    if((isGPSExtend)||(!mmiv_item1.getExtend())||(isThemeExtend)||(isDividerExtend)
					            ||(isPlusExtend)||(isRefreshExtend)||(isAccuracyExtend)||(isSMSRecordExtend))
			    {
				    setCollapseAll();  
			    }
			    else
			    {
				    setExpandAll();
			    }  
			 	    
			}
			
		}
		
		
		if(view.getId() == layout_gps.getId())
		{
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);      	
        	startActivityForResult(intent, 1);
        	AnimationManagerSystem.fromFade(PositionAssistantActivity.this);
		}
			
		
		if(view.getId()==mmiv_item1.getLayoutContract1Id())
		{
			dialog_contract1.show();
			dialog_contract1.setDialogTheme(State.STATE_THEME);

			Timer timer = new Timer();  
			timer.schedule(new TimerTask() 
			{  
			  
			    @Override  
			    public void run() 
			    {  
			        dialog_contract1.showKeyboard();  
			    }  
			}, 200); 
		}
		
		if(view.getId()==mmiv_item1.getLayoutContract2Id())
		{
			dialog_contract2.show();
			dialog_contract2.setDialogTheme(State.STATE_THEME);	
			Timer timer = new Timer();  
			timer.schedule(new TimerTask() 
			{  
			  
			    @Override  
			    public void run() 
			    {  
			        dialog_contract2.showKeyboard();  
			    }  
			}, 200); 
			
		}
		
		if(view.getId()==mmiv_item1.getLayoutContract3Id())
		{		
			dialog_contract3.show();
			dialog_contract3.setDialogTheme(State.STATE_THEME);
			
			Timer timer = new Timer();  
			timer.schedule(new TimerTask() 
			{  			  
			    @Override  
			    public void run() 
			    {  
			        dialog_contract3.showKeyboard();  
			    }  
			}, 200); 
		}
		
		
		if(view.getId() == R.id.layout_accuracy_remind_custom)
		{
			//弹出对话框设定精度
			dialog_accuracy.show();
			dialog_accuracy.setDialogTheme(State.STATE_THEME);
			
			Timer timer = new Timer();  
			timer.schedule(new TimerTask() 
			{  
			  
			    @Override  
			    public void run() 
			    {  
			        dialog_accuracy.showKeyboard();  
			    }  
			}, 200); 
			
			//InforToast.showInfor(PositionAssistantActivity.this, "提醒精度(10m)", State.STATE_THEME);
		}
		
		if(view.getId() == R.id.tv_sms_record)
		{
			//InforToast.showInfor(PositionAssistantActivity.this, "短信记录", State.STATE_THEME);
		}
		

		if(view.getId() == btn_save_setting.getId())
		{			
			saveSetting();
			InforToast.showInfor(PositionAssistantActivity.this,  "温馨提示：设置保存成功", State.STATE_THEME);
		}
		
		if(view.getId() == btn_exit.getId())
		{
			finish();
			AnimationManagerSystem.fromFade(PositionAssistantActivity.this);
		}
		
	}
	
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if(keyCode==KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN)
		{
		    if((System.currentTimeMillis()-exitTime) > 2000)
			{
		    	InforToast.showInfor(PositionAssistantActivity.this,  "再按一次退出程序", State.STATE_THEME);
		    	exitTime = System.currentTimeMillis();		      
		    } 
		    else 
		    {    
               finish();
               AnimationManagerSystem.fromFade(PositionAssistantActivity.this);
		    }    
		    return true;    
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	public void onGpsStatusChanged(int event) 
	{
		GpsStatus status = locationManager.getGpsStatus(null);
		GetGPSStatus(event, status);	
	}

	// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数  
	@Override
	public void onLocationChanged(Location location) 
	{	
	    Location currentLoaction = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        currentLoaction = checkLocation(location, currentLoaction);
		    
	    //updateView(location);     
        
        updateView(currentLoaction);
        
	}

    
	// Provider被disable时触发此函数，比如GPS被关闭  
	@Override
	public void onProviderDisabled(String provider) 
	{
		setPositionProvider(Double.valueOf(getCurrentFrequency()));
		updateView(null);  
		
		//InforToast.showInfor(PositionAssistantActivity.this, "onProviderDisabled()"+":"+provider,State.STATE_THEME);
	}


	// Provider被enable时触发此函数，比如GPS被打开  
	@Override
	public void onProviderEnabled(String provider) 
	{
		 setPositionProvider(Double.valueOf(getCurrentFrequency()));
		 location=locationManager.getLastKnownLocation(provider); 
		 updateView(location);
	}
	
	//当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		
	    if(status==LocationProvider.AVAILABLE)
	    {
	    	//InforManager.showInfor(PositionAssistantActivity.this, "当前GPS状态为可见状态");
	    }
	    
	    if(status==LocationProvider.OUT_OF_SERVICE)
	    {
	    	//InforManager.showInfor(PositionAssistantActivity.this, "当前GPS状态为服务区外状态");
	    }
	    
	    if(status==LocationProvider.TEMPORARILY_UNAVAILABLE)
	    {
	    	//InforManager.showInfor(PositionAssistantActivity.this, "当前GPS状态为暂停服务状态");
	    }	  
	    
	   // InforToast.showInfor(PositionAssistantActivity.this, "onStatusChanged()",State.STATE_THEME);
	    
	    
	}

	
}
