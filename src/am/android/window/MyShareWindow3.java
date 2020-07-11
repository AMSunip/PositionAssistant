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

public class MyShareWindow3 extends PopupWindow
{
	
	private LinearLayout    layout_share_cancle3             = null;    
	
	private RelativeLayout  layout_share_map_add_log        = null;
    private RelativeLayout  layout_share_map_copy_position  = null;
    private RelativeLayout  layout_share_map_cpoy_to_paster = null;
    private RelativeLayout  layout_share_map_send_position  = null;   
      
    
    private View            mMenuView3                      = null;  
	
	public MyShareWindow3(Activity context,OnClickListener listener)
	{
		super(context); 		
	    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    mMenuView3 = inflater.inflate(R.layout.layout_share_map, null);
	    
	    layout_share_cancle3=(LinearLayout)mMenuView3.findViewById(R.id.layout_share_map_cancel);  
	    
	    layout_share_map_send_position=(RelativeLayout)mMenuView3.findViewById(R.id.layout_share_map_send_position);
	    layout_share_map_send_position.setOnClickListener(listener);	    
	    layout_share_map_copy_position=(RelativeLayout)mMenuView3.findViewById(R.id.layout_share_map_copy_position);
	    layout_share_map_copy_position.setOnClickListener(listener);
	    layout_share_map_cpoy_to_paster=(RelativeLayout)mMenuView3.findViewById(R.id.layout_share_map_cpoy_to_paster);
	    layout_share_map_cpoy_to_paster.setOnClickListener(listener);
	    layout_share_map_add_log=(RelativeLayout)mMenuView3.findViewById(R.id.layout_share_map_add_log);
	    layout_share_map_add_log.setOnClickListener(listener);
	    
	    
	    layout_share_cancle3.setOnClickListener(new OnClickListener() 
	    {	
			@Override
			public void onClick(View v) 
			{
				dismiss();  
			}
		});
	    
	  //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView3);  
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
        mMenuView3.setOnTouchListener(new OnTouchListener()
        {    
            public boolean onTouch(View v, MotionEvent event)
            { 
                  
                int height = mMenuView3.findViewById(R.id.layout_share_map_pop).getTop();  
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