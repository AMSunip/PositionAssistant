package am.android.broadcast;

import am.android.consts.State;
import am.android.manager.InforToast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SMSSentReceiver extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		switch (getResultCode()) 
		{
            case Activity.RESULT_OK:
           // InforToast.showInfor(context, "短信已发送", State.STATE_THEME);
            break;
            
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                 InforToast.showInfor(context, "一般故障(Generic Failure)", State.STATE_THEME);
                 break;
            
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                 InforToast.showInfor(context, "无服务(No Service)", State.STATE_THEME);	
                 break;
            
            case SmsManager.RESULT_ERROR_NULL_PDU:
                 InforToast.showInfor(context, "空的协议描述单元(NULL PDU)", State.STATE_THEME);	
                 break;
            
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                 InforToast.showInfor(context, "无线电关闭(Radio Off)", State.STATE_THEME);	
                 break;
            
                 default:
                 break;
        }
        
     }
}

