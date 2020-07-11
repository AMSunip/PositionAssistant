package am.android.window;

import am.android.positionassistant.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MyShareWindow extends PopupWindow
{
   	
    private LinearLayout   layout_share_cancle   = null;    
    private RelativeLayout layout_share_wechat   = null;
    private RelativeLayout layout_share_message  = null;	
    private View           mMenuView             = null;  
	
	public MyShareWindow(Activity context,OnClickListener listener)
	{
		super(context); 		
	    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    mMenuView = inflater.inflate(R.layout.layout_share_dialog, null);
	    
	    layout_share_wechat=(RelativeLayout)mMenuView.findViewById(R.id.layout_share_wechat);
	    layout_share_message=(RelativeLayout)mMenuView.findViewById(R.id.layout_share_message);
	    layout_share_cancle=(LinearLayout)mMenuView.findViewById(R.id.layout_share_cancel);     
	    layout_share_wechat.setOnClickListener(listener); 
	    layout_share_message.setOnClickListener(listener);	  
	    
	    layout_share_cancle.setOnClickListener(new OnClickListener() 
	    {	
			@Override
			public void onClick(View v) 
			{
				dismiss();  
			}
		});
	    
	  //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView);  
        //����SelectPicPopupWindow��������Ŀ�  
        this.setWidth(LayoutParams.FILL_PARENT);  
        //����SelectPicPopupWindow��������ĸ�  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        //����SelectPicPopupWindow��������ɵ��  
        this.setFocusable(true);  
        //����SelectPicPopupWindow�������嶯��Ч��  
        // this.setAnimationStyle(R.style.AnimBottom); 
        //ʵ����һ��ColorDrawable��ɫΪ��͸��  
        ColorDrawable dw = new ColorDrawable(0xb0000000);  
        //����SelectPicPopupWindow��������ı���  
        this.setBackgroundDrawable(dw);  
        //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����     
        mMenuView.setOnTouchListener(new OnTouchListener()
        {    
            public boolean onTouch(View v, MotionEvent event)
            { 
                  
                int height = mMenuView.findViewById(R.id.layout_share_pop).getTop();  
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP)
                {  
                    if(y<height)
                    {  
                        dismiss();  
                    }  
                }                 
                return true;  
            }  
        });     
    }
}