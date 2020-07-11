package am.android.broadcast;

import am.android.consts.State;
import am.android.manager.InforToast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SMSDeliveredReceiver extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		switch(getResultCode())
		{
          case Activity.RESULT_OK:
        	   String intent_information =  intent.getExtras().get("INTENT_INFORMATION").toString();
        	   InforToast.showInfor(context, intent_information+"�ѽ�����Ϣ", State.STATE_THEME);
               break;
             
          case Activity.RESULT_CANCELED:
        	   InforToast.showInfor(context, "���ŷ���ʧ��", State.STATE_THEME);
               break;
        }
	}
}
