package am.android.msg;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class MessageSender 
{
    public static void sendMessage(Context context,String phone, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        PendingIntent piSent=PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
        PendingIntent piDelivered=PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), 0);
        sms.sendTextMessage(phone, null, message, piSent, piDelivered);
    }
    
    public static void sendMessage(Context context,String phone, String message,String Contract,String Number)
    {	
        SmsManager sms = SmsManager.getDefault();
        PendingIntent piSent=PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
        Intent intent = new Intent("SMS_DELIVERED");
        intent.putExtra("INTENT_INFORMATION", Contract+"("+Number+")");        
        PendingIntent piDelivered=PendingIntent.getBroadcast(context, 0, intent, 0);   
        sms.sendTextMessage(phone, null, message, piSent, piDelivered);
    }
    
    public static void sendMessage1(Context context,String phone, String message,String Contract,String Number)
    {	
        SmsManager sms = SmsManager.getDefault();
        PendingIntent piSent=PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
        Intent intent = new Intent("SMS_DELIVERED1");
        intent.putExtra("INTENT_INFORMATION", Contract+"("+Number+")");        
        PendingIntent piDelivered=PendingIntent.getBroadcast(context, 0, intent, 0);   
        sms.sendTextMessage(phone, null, message, piSent, piDelivered);
    }
    
    public static void sendMessage2(Context context,String phone, String message,String Contract,String Number)
    {
        SmsManager sms = SmsManager.getDefault(); 
        PendingIntent piSent=PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
        Intent intent = new Intent("SMS_DELIVERED2");
        intent.putExtra("INTENT_INFORMATION", Contract+"("+Number+")");        
        PendingIntent piDelivered=PendingIntent.getBroadcast(context, 0, intent, 0);   
        sms.sendTextMessage(phone, null, message, piSent, piDelivered);
    }
    
    public static void sendMessage3(Context context,String phone, String message,String Contract,String Number)
    {
        SmsManager sms = SmsManager.getDefault(); 
        PendingIntent piSent=PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
        Intent intent = new Intent("SMS_DELIVERED3");
        intent.putExtra("INTENT_INFORMATION", Contract+"("+Number+")");        
        PendingIntent piDelivered=PendingIntent.getBroadcast(context, 0, intent, 0);   
        sms.sendTextMessage(phone, null, message, piSent, piDelivered);
    }
      
}
