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

public class MyShareWindow2 extends PopupWindow
{

	private LinearLayout   layout_share_cancle2   = null;
    
    private RelativeLayout layout_share_wechat2   = null;
    private RelativeLayout layout_share_message2  = null;
	
    private View           mMenuView2             = null;  
	
	public MyShareWindow2(Activity context,OnClickListener listener)
	{
		super(context); 
		
	    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	    mMenuView2 = inflater.inflate(R.layout.layout_share_dialog2, null);
	    
	    layout_share_wechat2=(RelativeLayout)mMenuView2.findViewById(R.id.layout_share_wechat2);
	    layout_share_message2=(RelativeLayout)mMenuView2.findViewById(R.id.layout_share_message2);
	    layout_share_cancle2=(LinearLayout)mMenuView2.findViewById(R.id.layout_share_cancel2);
	     
	    layout_share_wechat2.setOnClickListener(listener);
	    
	    layout_share_message2.setOnClickListener(listener);	  
	    
	    layout_share_cancle2.setOnClickListener(new OnClickListener() 
	    {
			
			@Override
			public void onClick(View v) 
			{
				dismiss();  
			}
		});
	    
	  //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView2);  
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
	  
      
        mMenuView2.setOnTouchListener(new OnTouchListener()
        {  
            
            public boolean onTouch(View v, MotionEvent event)
            {  
                  
                int height = mMenuView2.findViewById(R.id.layout_share_pop2).getTop();  
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