package am.android.activity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapScreenShotListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import am.android.broadcast.SMSDeliveredReceiver;
import am.android.broadcast.SMSSentReceiver;
import am.android.consts.Consts;
import am.android.consts.GPSLocation;
import am.android.consts.State;
import am.android.core.Launch;
import am.android.csv.CSVWriter;
import am.android.dialog.MyDialogAddLog;
import am.android.gpscorrect.CoordinateConversion;
import am.android.manager.DatabaseManager;
import am.android.manager.FormatManager;
import am.android.manager.InforManager;
import am.android.manager.InforToast;
import am.android.manager.MobileNumberManager;
import am.android.msg.MessageSender;
import am.android.positionassistant.R;
import am.android.window.MyShareWindow;
import am.android.window.MyShareWindow3;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class AMapActivity extends Activity implements OnClickListener,GpsStatus.Listener,LocationListener,InfoWindowAdapter,OnInfoWindowClickListener
{
		
	private AMap                     aMap                     = null;
	
	private Boolean                  isFirst                  = true;
	
	private ClipboardManager         myClipboard              = null;
	private ClipData                 myClip                   = null;
	
	private ImageButton              imgbtn_center_locked     = null;
	private ImageButton              imgbtn_maptype           = null;
	private ImageButton              imgbtn_menu_map          = null;
	private ImageButton              imgbtn_pos               = null;
	private ImageButton              imgbtn_screenshot        = null;
		
	private LatLng                   latLng                   = null;
	private LocationManager          locationManager          = null;
	private Location                 location                 = null;
	
	private MapView                  mapView                  = null;
	private Marker                   marker                   = null;
    private MarkerOptions            options                  = null;
	
    private MyDialogAddLog           dialog_add_log           = null;
	private MyShareWindow            my_share_window          = null;
    private MyShareWindow3           my_share_window3         = null;

    
    private SMSDeliveredReceiver     smsDeliveredReceiver1    = null;
    private SMSDeliveredReceiver     smsDeliveredReceiver2    = null;
    private SMSDeliveredReceiver     smsDeliveredReceiver3    = null;
    private SMSSentReceiver          smsSentReceiver          = null;
    
    private static final int         TWO_MINUTES              = 1000 * 60 * 2;
   
    private int                      acc                      = 0; 
    private int                      alt                      = 0;
    private double                   lat                      = 30.541093;
    private double                   lng                      = 114.360734;
    
    private int                      temp_acc                 = 0;
    private int                      temp_alt                 = 0;
    private double                   temp_lat                 = 30.541093;
    private double                   temp_lng                 = 114.360734;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		MapsInitializer.sdcardDir = getSdCacheDir(this);
		setContentView(R.layout.activity_amap);
		
		myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
		locationManager.addGpsStatusListener(this);
		mapView = (MapView)findViewById(R.id.amap);
		mapView.onCreate(savedInstanceState);		
		
		init();		
	}
	
	
	@Override  
	protected void onDestroy() 
	{  
	     super.onDestroy();  
	     mapView.onDestroy();  
	     updateView(null);  
	     
	}  
	
	
	@Override  
	protected void onPause()
	{  
	    super.onPause();  
	    mapView.onPause();  
	    
	    unregisterReceiver(smsSentReceiver);
	    unregisterReceiver(smsDeliveredReceiver1);
	    unregisterReceiver(smsDeliveredReceiver2);
	    unregisterReceiver(smsDeliveredReceiver3);	     
	}
	
	 
	@Override  
	protected void onResume() 
	{  
	   super.onResume();  
	   mapView.onResume();  
	   
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
    protected void onSaveInstanceState(Bundle outState)
	{  
	    super.onSaveInstanceState(outState);  
	    mapView.onSaveInstanceState(outState);  
	}
	 
	 
	@Override 
	protected void onStart()
	{
		super.onStart();
		isFirst = true;
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
	 
	
	/******公版******/
	private String getLogString(String action)
	{
		String result = "";
		
		result ="'"+ FormatManager.getSystemTimeString()+","
		        +"'"+FormatManager.getEditTextStringFormat(String.valueOf(GPSLocation.GPS_LOCATION_LATITUDE_D))+","
				+"'"+FormatManager.getEditTextStringFormat(String.valueOf(GPSLocation.GPS_LOCATION_LONGITUDE_D))+","	
				+FormatManager.getTextViewStringFormat(String.valueOf(GPSLocation.GPS_LOCATION_ALTITUDE_D))+","
			    +FormatManager.getTextViewStringFormat(String.valueOf(GPSLocation.GPS_LOCATION_ACCURACY_D))+","
		        +action;
		
		return result;
	}
	/******公版******/
	

	private String getLogString(String action,String place,String remark)
	{
		String result = "";
		
		result ="'"+ FormatManager.getSystemTimeString()+","	
		        +"'"+String.valueOf(temp_lat)+","
				+"'"+String.valueOf(temp_lng)+","
				+String.valueOf(temp_alt)+","
			    +String.valueOf(temp_acc)+","
		        +action+","
		        +","+","+","
		        +place+","
		        +remark;
		
		return result;
	}

	
	//STATE_PLUS_SP
	private String getMainMessage1()
	{
		String message = "";
			
		message =FormatManager.getMessageFormat(String.valueOf(GPSLocation.GPS_LOCATION_LATITUDE_D),String.valueOf(GPSLocation.GPS_LOCATION_LONGITUDE_D));
		
		if(State.STATE_PLUS_SP.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(String.valueOf(temp_lat),
					                                        String.valueOf(temp_lng), 
					                                        String.valueOf(temp_alt), 
					                                        String.valueOf(temp_acc));
		}
		
		return message;
	}
	
	 
	private String getMainMessage2()
	{
		String message = "";
			
		message =FormatManager.getMessageFormat(String.valueOf(GPSLocation.GPS_LOCATION_LATITUDE_D),String.valueOf(GPSLocation.GPS_LOCATION_LONGITUDE_D));
		
		if(State.STATE_PLUS_CS.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(String.valueOf(temp_lat),
                                                            String.valueOf(temp_lng), 
                                                            String.valueOf(temp_alt), 
                                                            String.valueOf(temp_acc));
		}
		
		return message;
	}
	 
	 
	private String getMainMessage3()
	{
		String message = "";
		
		message =FormatManager.getMessageFormat(String.valueOf(GPSLocation.GPS_LOCATION_LATITUDE_D),String.valueOf(GPSLocation.GPS_LOCATION_LONGITUDE_D));
	
		if(State.STATE_PLUS_CP.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(String.valueOf(temp_lat),
                                                            String.valueOf(temp_lng), 
                                                            String.valueOf(temp_alt), 
                                                            String.valueOf(temp_acc));
		}
	
		return message;
	}
	
	
	private String getMainMessage4()
	{
		
        String message = "";
		
		message =FormatManager.getMessageFormat(String.valueOf(GPSLocation.GPS_LOCATION_LATITUDE_D),String.valueOf(GPSLocation.GPS_LOCATION_LONGITUDE_D));
	
		if(State.STATE_PLUS_RL.equals("1"))
		{
			message=FormatManager.getMessageFormatForDetail(String.valueOf(temp_lat),
                                                            String.valueOf(temp_lng), 
                                                            String.valueOf(temp_alt), 
                                                            String.valueOf(temp_acc));
		}
	
		return message;
	}	
	 
	 
	private void getScreenShot(Bitmap bitmap)
    {
			
		String filePath=Consts.PATH_SDCARD+"//PositionAssistant//Pictures//"+Consts.sdf.format(new Date())+".png";
		try 
		{
			FileOutputStream fos=new FileOutputStream(filePath);	
			boolean b=bitmap.compress(CompressFormat.PNG, 100, fos);
			
			try
			{
				fos.flush();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				fos.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			if(b)
			{
				//copyBitmap(filePath);
				//InforToast.showInfor(AMapActivity.this, filePath,"1");
				InforToast.showInfor(AMapActivity.this, "温馨提示:截屏成功","1");
			}
			else
			{
				InforToast.showInfor(AMapActivity.this, "温馨提示:截屏失败", "1");
			}	
		}
	    catch (FileNotFoundException e) 
	    {			
			e.printStackTrace();
	    }	
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
	
	
	private String getSdCacheDir(Context context)
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
	   	{ 
	   	 	 File fExternalStorageDirectory = Environment.getExternalStorageDirectory();
	   	 	 File autonaviDir = new File( fExternalStorageDirectory, "autonavi"); 
	   	 	 boolean result = false; 
	   	 	
	   	 	 if (!autonaviDir.exists())
	   	 	 { 
	   	 	  	result = autonaviDir.mkdir(); 
	   	 	 } 
	   	 	 
	   	 	 File minimapDir = new File(autonaviDir, "Name"); 
	   	 	 if (!minimapDir.exists())
	   	 	 { 
	   	 	  	result = minimapDir.mkdir(); 
	         } 
	            //return minimapDir.toString() + "/";
	            return autonaviDir.toString() + "/";
	    } 
	    else 
	    { 
		    return null;
	    }
      }
	
	
	private void init()
	{
		initView();
	}	
	
	
	private void initMap()
	{
		
		aMap    = mapView.getMap();	
		options = new MarkerOptions();
		
	    aMap.getUiSettings().setCompassEnabled(true);
		aMap.getUiSettings().setScaleControlsEnabled(true);
		aMap.getUiSettings().setZoomControlsEnabled(true);
		aMap.getUiSettings().setScrollGesturesEnabled(true);
		aMap.getUiSettings().setRotateGesturesEnabled(true);
		aMap.getUiSettings().setTiltGesturesEnabled(true);
		aMap.setTrafficEnabled(true);		 
	
		aMap.setInfoWindowAdapter(this);
		aMap.setOnInfoWindowClickListener(this);		
		
		
		if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
		{
			
			try
			{
				onPos(GPSLocation.GPS_LOCATION_ALTITUDE_D,GPSLocation.GPS_LOCATION_ACCURACY_D,GPSLocation.GPS_LOCATION_LATITUDE_D,GPSLocation.GPS_LOCATION_LONGITUDE_D,true);
			}
			catch(Exception ex)
			{
				InforToast.showInfor(AMapActivity.this, ex.toString(), "1");
			}
		}
			
	}
	
	
	private void initView()
	{	
		initMap();		
	
		imgbtn_center_locked = (ImageButton)findViewById(R.id.imgbtn_center_locked);
		imgbtn_center_locked.setOnClickListener(this);
		
		imgbtn_menu_map = (ImageButton)findViewById(R.id.imgbtn_menu_map);
		imgbtn_menu_map.setOnClickListener(this);
		
		imgbtn_screenshot = (ImageButton)findViewById(R.id.imgbtn_screenshot);
		imgbtn_screenshot.setOnClickListener(this);
		
		imgbtn_maptype = (ImageButton)findViewById(R.id.imgbtn_maptype);
		imgbtn_maptype.setOnClickListener(this);
		
		imgbtn_pos = (ImageButton)findViewById(R.id.imgbtn_pos);
		imgbtn_pos.setOnClickListener(this);	
		
		
		if(State.STATE_MAP_LOCKED.endsWith("1"))
		{
			imgbtn_center_locked.setBackgroundResource(R.drawable.center_locked);
		}
		else
		{
			imgbtn_center_locked.setBackgroundResource(R.drawable.center_unlocked);
		}
		
		
		dialog_add_log = new MyDialogAddLog(AMapActivity.this,new OnClickListener()
		{
				@Override
				public void onClick(View v)
				{
					
					if(v.getId()==R.id.tv_dialog_add_log_ok)
					{
						if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
						{
							String place = dialog_add_log.getPlaceText();
							String remark = dialog_add_log.getRemarkText();
							
							//if(!((place.equals(""))&&(remark.equals(""))))
							//{
							    //记录坐标时，可以不输入【地点】以及【备注信息】
								try
								{
								 
								    CSVWriter.fileExist(Consts.file_log);					
								    CSVWriter.writeText(getLogString("记录当前坐标到日志",place,remark));
								    InforToast.showInfor(AMapActivity.this, "已记录当前坐标","1");
								}
								catch(Exception ex)
								{
									 InforToast.showInfor(AMapActivity.this, ex.toString(),"1");
								}
							//}
						}
						else
						{
							InforToast.showInfor(AMapActivity.this,  "定位尚未成功，请稍后重试", "1");
						}				
							
					}
					
					if(v.getId()==R.id.tv_dialog_add_log_cancel)
					{
						
					}
				   
					dialog_add_log.dismiss();
				}
		    	
		});
		
	}
	
	
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
 
    
    private boolean isSameProvider(String provider1, String provider2)
    {
        if (provider1 == null)
        {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
	
	
    private void onPos(Location location,String maplocked)
    {
    	 aMap.clear();
			
	     acc      = FormatManager.getAccuracyFormat(location.getAccuracy());
	     alt      = FormatManager.getAccuracyFormat(location.getAltitude());
	     lat      = FormatManager.getPositionFormat(CoordinateConversion.wgs_gcj_encrypt(location.getLatitude(),location.getLongitude()).getLat());
	     lng      = FormatManager.getPositionFormat(CoordinateConversion.wgs_gcj_encrypt(location.getLatitude(),location.getLongitude()).getLng());
	
	     latLng = new LatLng(lat, lng);
	
	     options.title("A:"+acc+"m"+"  "+"H:"+alt+"m");
	
	     options.position(latLng);  	     
         options.perspective(true);  
         options.icon(BitmapDescriptorFactory.fromResource(R.drawable.navi_popup)); 
         options.snippet("B:"+  String.valueOf(FormatManager.getPositionFormat(location.getLatitude()))+"\n"+"L:"+String.valueOf(FormatManager.getPositionFormat(location.getLongitude())));
         marker = aMap.addMarker(options);
         marker.showInfoWindow(); 
         
        
  
         
         if(isFirst)
         {
        	 isFirst = false;      	
        	 
            // aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        	 aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
         }
         else
         {
        	 //地图中心已锁定
             if(maplocked.equals("1"))
             {
            	 //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16)); 
            	 aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
             }
             //地图中心已解锁
             else
             {
            	
             }
         }
         
    }
    
    
    private void onPos(double altitude,double accuracy,double latitude,double longitude,boolean isZoom)
    {
    	 aMap.clear();
			
	     acc      = FormatManager.getAccuracyFormat(accuracy);
	     alt      = FormatManager.getAccuracyFormat(altitude);
	     lat      = FormatManager.getPositionFormat(CoordinateConversion.wgs_gcj_encrypt(latitude,longitude).getLat());
	     lng      = FormatManager.getPositionFormat(CoordinateConversion.wgs_gcj_encrypt(latitude,longitude).getLng());
	
	     latLng = new LatLng(lat, lng);
	
	     options.title("A:"+acc+"m"+"  "+"H:"+alt+"m");
	
	     options.position(latLng);  	     
         options.perspective(true);  
         options.icon(BitmapDescriptorFactory.fromResource(R.drawable.navi_popup)); 
         options.snippet("B:"+String.valueOf(latitude)+"\n"+"L:"+String.valueOf(longitude));
         marker = aMap.addMarker(options);
         marker.showInfoWindow(); 
         
         //第一次定位，设置地图中心点以及显示级别
         if(isZoom)
         {
        	 aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));        
         }
         
         else
         {
        	 aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
         }
            
    }  
    
    
   /* private void onPos(double altitude,double accuracy,double latitude,double longitude,String maplocked)
    {
    	 aMap.clear();
			
	     acc      = FormatManager.getAccuracyFormat(accuracy);
	     alt      = FormatManager.getAccuracyFormat(altitude);
	     lat      = FormatManager.getPositionFormat(CoordinateConversion.wgs_gcj_encrypt(latitude,longitude).getLat());
	     lng      = FormatManager.getPositionFormat(CoordinateConversion.wgs_gcj_encrypt(latitude,longitude).getLng());
	
	     latLng = new LatLng(lat, lng);
	
	     options.title("A:"+acc+"m"+"  "+"H:"+alt+"m");
	
	     options.position(latLng);  	     
         options.perspective(true);  
         options.icon(BitmapDescriptorFactory.fromResource(R.drawable.navi_popup)); 
         options.snippet("B:"+String.valueOf(latitude)+"\n"+"L:"+String.valueOf(longitude));
         marker = aMap.addMarker(options);
         marker.showInfoWindow(); 
         
         //地图中心已锁定
         if(maplocked.equals("1"))
         {
        	 //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16)); 
        	 aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
         }
         //地图中心已解锁
         else
         {
        	
         }
            
    }*/
    
    
    private void render(Marker marker,View convertView)
	{				
		  TextView tv_marker_title=(TextView)convertView.findViewById(R.id.tv_marker_title);
		  TextView tv_marker_text=(TextView)convertView.findViewById(R.id.tv_marker_text);	
		  tv_marker_title.setText(marker.getTitle());
		  tv_marker_text.setText(marker.getSnippet());	
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
			 
			  try
			  {
				  onPos(location,State.STATE_MAP_LOCKED);	
				  
				  GPSLocation.GPS_LOCATION_ACCURACY_D  = FormatManager.getAccuracyFormat(location.getAccuracy());
				  GPSLocation.GPS_LOCATION_ALTITUDE_D  = FormatManager.getAltitudeFormat(location.getAltitude());		
				  GPSLocation.GPS_LOCATION_LATITUDE_D  = FormatManager.getPositionFormat(location.getLatitude());
				  GPSLocation.GPS_LOCATION_LONGITUDE_D = FormatManager.getPositionFormat(location.getLongitude());
				  				
			  }
			  catch(Exception ex)
			  {
				  InforToast.showInfor(AMapActivity.this, ex.toString(), "1");
			  }
		}
		else
		{
			
		}		
			
	}
	 
	 
	@Override
	public void onLocationChanged(Location location)
	{	
		   Location currentLoaction = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
           currentLoaction = checkLocation(location, currentLoaction); 
    	   //updateView(location);
           updateView(currentLoaction);
	}


	@Override
	public void onProviderDisabled(String provider) 
	{
		setPositionProvider(1);
		updateView(null); 		
	}
	

	@Override
	public void onProviderEnabled(String provider) 
	{
		 setPositionProvider(1);
		 location=locationManager.getLastKnownLocation(provider); 
		 updateView(location);
		 
	}


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
		    
		   // InforToast.showInfor(PositionAssistantActivity.this, "onStatusChanged()","1");
		    
	}


	@Override
	public void onGpsStatusChanged(int event) 
	{
		GpsStatus status = locationManager.getGpsStatus(null);
		GetGPSStatus(event, status);	
		
	}
	
	
	@Override
	public View getInfoContents(Marker marker) 
	{
		LayoutInflater inflater=LayoutInflater.from(AMapActivity.this);
		View convertView=inflater.inflate(R.layout.item_marker, null);
		
		render(marker,convertView);
		return convertView;
	}


	@Override
	public View getInfoWindow(Marker marker) 
	{
		
		return null;
	}
	

	@Override
	public void onInfoWindowClick(Marker marker) 
	{	
		temp_lat = GPSLocation.GPS_LOCATION_LATITUDE_D;
		temp_lng = GPSLocation.GPS_LOCATION_LONGITUDE_D;
		temp_alt = GPSLocation.GPS_LOCATION_ALTITUDE_D;
		temp_acc = GPSLocation.GPS_LOCATION_ACCURACY_D;
		
		my_share_window3 = new MyShareWindow3(AMapActivity.this, this);          
		//设置layout在PopupWindow中显示的位置  
		my_share_window3.showAtLocation(AMapActivity.this.findViewById(R.id.imgbtn_maptype), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
			
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
				
			    DatabaseManager.rewriteSMSInfor(AMapActivity.this);
				
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

	
	@Override
	public void onClick(View v)
	{
		
		//锁定地图中心
		if(v.getId()==imgbtn_center_locked.getId())
		{
			if(State.STATE_MAP_LOCKED.equals("1"))
			{
				  imgbtn_center_locked.setBackgroundResource(R.drawable.center_unlocked);
				  InforToast.showInfor(AMapActivity.this, "温馨提示:地图中心已解锁","1");
				  State.STATE_MAP_LOCKED = "0";
			}
			else
			{
				  imgbtn_center_locked.setBackgroundResource(R.drawable.center_locked);
				  InforToast.showInfor(AMapActivity.this, "温馨提示:地图中心已锁定","1");
				  State.STATE_MAP_LOCKED = "1";
			}			  		  
		}
		
		
		if(v.getId()==imgbtn_menu_map.getId())
		{
			  my_share_window3 = new MyShareWindow3(AMapActivity.this, this);          
			//设置layout在PopupWindow中显示的位置  
			  my_share_window3.showAtLocation(AMapActivity.this.findViewById(R.id.imgbtn_maptype), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
		}
		
		
		//一键短信发送坐标
		if(v.getId()==R.id.layout_share_map_send_position)
		{		
			
			  my_share_window3.dismiss();	
			 		 
				  
		      if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
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
					    	MessageSender.sendMessage1(AMapActivity.this, State.STATE_NUMBER1, message,FormatManager.getContract(State.STATE_CONTRACT1, 1),State.STATE_NUMBER1);
					    	DatabaseManager.writerDataToSMSFile(AMapActivity.this,Launch.getSystemDate(), Launch.getSystemTime(), State.STATE_CONTRACT1, State.STATE_NUMBER1, message);
				    		initSMSInforFile();
					    	appendix=appendix+","+State.STATE_CONTRACT1+"("+State.STATE_NUMBER1+")";
					    }
					    	
					    if(isNumber2)
					    {
					    	MessageSender.sendMessage2(AMapActivity.this, State.STATE_NUMBER2, message,FormatManager.getContract(State.STATE_CONTRACT2, 2),State.STATE_NUMBER2);
					    	DatabaseManager.writerDataToSMSFile(AMapActivity.this,Launch.getSystemDate(), Launch.getSystemTime(), State.STATE_CONTRACT2, State.STATE_NUMBER2, message);
				    		initSMSInforFile();
					    	appendix=appendix+","+State.STATE_CONTRACT2+"("+State.STATE_NUMBER2+")";
					    }
					    	
					    if(isNumber3)
					    {
					    	MessageSender.sendMessage3(AMapActivity.this, State.STATE_NUMBER3, message,FormatManager.getContract(State.STATE_CONTRACT3, 3),State.STATE_NUMBER3);
					    	DatabaseManager.writerDataToSMSFile(AMapActivity.this,Launch.getSystemDate(), Launch.getSystemTime(), State.STATE_CONTRACT3, State.STATE_NUMBER3, message);
				    		initSMSInforFile();
					    	appendix=appendix+","+State.STATE_CONTRACT3+"("+State.STATE_NUMBER3+")";
					    }			    	
					    	
					    CSVWriter.fileExist(Consts.file_log);					
						CSVWriter.writeText(getLogString("一键短信发送坐标")+appendix);  	
					   }
					   else
					   {	
					        InforToast.showInfor(AMapActivity.this, "温馨提示:请先设置联系人","1");
					   }	
					}
					else
					{
				    	InforToast.showInfor(AMapActivity.this, "定位尚未成功，请稍后重试","1");
					}					    				 		
		}
		
		//复制并分享坐标
		if(v.getId()==R.id.layout_share_map_copy_position)
		{
			my_share_window3.dismiss();
			
			//调用微信
			try
			{	
				if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
				{
					String message = getMainMessage2();					
					copy(message);			
					CSVWriter.fileExist(Consts.file_log);					
					CSVWriter.writeText(getLogString("复制并分享坐标"));	
					
					//Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");  
				    //startActivity(intent); 
					
					my_share_window = new MyShareWindow(AMapActivity.this, this);          
					//设置layout在PopupWindow中显示的位置  
					my_share_window.showAtLocation(AMapActivity.this.findViewById(R.id.imgbtn_menu_map), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
					
				}
				else
				{
					InforManager.showInfor(AMapActivity.this, "定位尚未成功，请稍后重试");
				}
			}
			catch(Exception e)
			{
				InforToast.showInfor(AMapActivity.this,  "温馨提示：没有找到本地应用", "1");
			}	
			
		}
		
		//复制坐标到剪贴板
		if(v.getId()==R.id.layout_share_map_cpoy_to_paster)
		{
			my_share_window3.dismiss();	
			
			if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
			{
				String message = getMainMessage3();					
				copy(message);
				CSVWriter.fileExist(Consts.file_log);					
				CSVWriter.writeText(getLogString("复制坐标到剪贴板"));				
				InforToast.showInfor(AMapActivity.this,  "已复制当前坐标", "1");	
							
			}
			else
			{
				InforToast.showInfor(AMapActivity.this,  "定位尚未成功，请稍后重试", "1");
			}
			
		
		}
		
		
		//记录当前坐标到日志
		if(v.getId()==R.id.layout_share_map_add_log)
		{
			
			my_share_window3.dismiss();		  
	
			if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
			{
			    String message = getMainMessage4();
			    copy(message);
			    
			    temp_lat = GPSLocation.GPS_LOCATION_LATITUDE_D;
			    temp_lng = GPSLocation.GPS_LOCATION_LONGITUDE_D;
			    temp_alt = GPSLocation.GPS_LOCATION_ALTITUDE_D;
			    temp_acc = GPSLocation.GPS_LOCATION_ACCURACY_D;
			    
			
			    dialog_add_log.show();
		        dialog_add_log.setDialogTheme("0");
		    				   
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
				InforToast.showInfor(AMapActivity.this,  "定位尚未成功，请稍后重试", "1");
			}		
		}
		
		
		if((v.getId()==R.id.layout_share_wechat))
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
				InforToast.showInfor(AMapActivity.this,  "温馨提示：没有找到本地应用", State.STATE_THEME);
			}
		}
		
		
		
		
		if((v.getId()==R.id.layout_share_message))
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
				InforToast.showInfor(AMapActivity.this,  "温馨提示：没有找到本地应用", State.STATE_THEME);
			}
				
		}
		
		
		
		
		
		
		
		
		if(v.getId()==imgbtn_screenshot.getId())
		{

			aMap.getMapScreenShot(new OnMapScreenShotListener() 
			{
				
				@Override
				public void onMapScreenShot(Bitmap bitmap, int position)
				{
				
				}
				
				@Override
				public void onMapScreenShot(Bitmap bitmap)
				{
					getScreenShot(bitmap);
				}
			});
		}
		
		if(v.getId()==imgbtn_maptype.getId())
		{
			if(aMap.getMapType() == AMap.MAP_TYPE_NORMAL)
			{
				aMap.setMapType(AMap.MAP_TYPE_SATELLITE);	
				InforToast.showInfor(AMapActivity.this, "当前地图:卫星地图", "1");
			}
			else
			{
				aMap.setMapType(AMap.MAP_TYPE_NORMAL);
				InforToast.showInfor(AMapActivity.this, "当前地图:普通地图", "1");
			}
		}
		
		
		if(v.getId()==imgbtn_pos.getId())
		{
			if((GPSLocation.GPS_LOCATION_LATITUDE_D!=30.541093)&&(GPSLocation.GPS_LOCATION_LONGITUDE_D!=114.360734))
			{
				
				try
				{
					 if(State.STATE_MAP_LOCKED.endsWith("1"))
		        	 {
						 onPos(GPSLocation.GPS_LOCATION_ALTITUDE_D,GPSLocation.GPS_LOCATION_ACCURACY_D,GPSLocation.GPS_LOCATION_LATITUDE_D,GPSLocation.GPS_LOCATION_LONGITUDE_D,true);
		        	 }
		        	 else
		        	 {
		        		onPos(GPSLocation.GPS_LOCATION_ALTITUDE_D,GPSLocation.GPS_LOCATION_ACCURACY_D,GPSLocation.GPS_LOCATION_LATITUDE_D,GPSLocation.GPS_LOCATION_LONGITUDE_D,false);
		        	 }
					
				
				}
				catch(Exception ex)
				{
					InforToast.showInfor(AMapActivity.this, ex.toString(), "1");
				}											
			}	
		}
		
	}


}
