package am.android.dialog;


import am.android.consts.Consts;
import am.android.consts.DeviceInfor;
import am.android.positionassistant.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialogAddLog implements OnClickListener
{
	
	private AlertDialog       dialog                          = null;
	private Context           context                         = null;
	private OnClickListener   listener                        = null;
	private EditText          et_dialog_place                 = null;
	private EditText          et_dialog_remark                = null;
	
	private LinearLayout      layout_dialog_add_log           = null;
	private LinearLayout      layout_dialog_add_log_divider1  = null;
	private LinearLayout      layout_dialog_add_log_divider2  = null;
	private LinearLayout      layout_dialog_add_log_divider3  = null;
	
	private TextView          tv_dialog_add_log               = null;
	private TextView          tv_dialog_add_log_ok            = null;
	private TextView          tv_dialog_add_log_cancel        = null;
	private TextView          tv_dialog_place                 = null;
	private TextView          tv_dialog_remark                = null;
	private String            infor_name                      = null;
	private String            infor_number                    = null;
	
	private WindowManager     windowManager                   = null;
	private Display           diaplay                         = null;
	
	private int               width                           = 810;
	
	public MyDialogAddLog(Context context)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
	}
	
	public MyDialogAddLog(Context context,OnClickListener listener)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
		this.listener=listener;
	}
	
	public MyDialogAddLog(Context context,OnClickListener listener,String infor_name,String infor_number)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
		this.listener=listener;
		this.infor_name=infor_name; 
		
		if(!infor_number.equals("新建联系人"))
		{
			this.infor_number=infor_number;
		}		
	}	
	
	public void setDialogTheme(String THEME)
	{
		if(THEME.equals("1"))
		{
			//炫酷黑			
			tv_dialog_add_log.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_dialog_place.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_dialog_remark.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			
			et_dialog_place.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			et_dialog_remark.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_dialog_add_log_ok.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			
			layout_dialog_add_log.setBackgroundResource(R.drawable.style_border_dialog2);
			layout_dialog_add_log_divider1.setBackgroundResource(R.drawable.divider_black);
			layout_dialog_add_log_divider2.setBackgroundResource(R.drawable.divider_black);
			layout_dialog_add_log_divider3.setBackgroundResource(R.drawable.divider_black);			
			
		}
		else
		{
			//简约白			
			tv_dialog_add_log.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_dialog_place.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_dialog_remark.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			
			et_dialog_place.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			et_dialog_remark.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_dialog_add_log_ok.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			
			layout_dialog_add_log.setBackgroundResource(R.drawable.style_border_dialog);
			layout_dialog_add_log_divider1.setBackgroundResource(R.drawable.divider_default);
			layout_dialog_add_log_divider2.setBackgroundResource(R.drawable.divider_default);
			layout_dialog_add_log_divider3.setBackgroundResource(R.drawable.divider_default);
		}
	}
	
	
	public void show()
	{		
		dialog.setView(new EditText(context));
		dialog.show();	
		
		Window  window = dialog.getWindow();		
		WindowManager.LayoutParams lp = window.getAttributes();	
		width =(int)(DeviceInfor.SCREEN_WIDTH*0.75); 
		
		lp.width =width;
	    //lp.height = 300; // 高度
	    //lp.alpha = 1.0f; // 透明度
	    // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
	    // dialog.onWindowAttributesChanged(lp);
	    window.setAttributes(lp);
		
		window.setContentView(R.layout.dialog_add_log);		
	   
		layout_dialog_add_log=(LinearLayout)window.findViewById(R.id.layout_dialog_add_log);
        layout_dialog_add_log_divider1=(LinearLayout)window.findViewById(R.id.layout_dialog_add_log_divider1);
        layout_dialog_add_log_divider2=(LinearLayout)window.findViewById(R.id.layout_dialog_add_log_divider2);
        layout_dialog_add_log_divider3=(LinearLayout)window.findViewById(R.id.layout_dialog_add_log_divider3);
        
        tv_dialog_add_log=(TextView)window.findViewById(R.id.tv_dialog_add_log);
        tv_dialog_place=(TextView)window.findViewById(R.id.tv_dialog_place);
        tv_dialog_remark=(TextView)window.findViewById(R.id.tv_dialog_remark);
        
		et_dialog_place=(EditText)window.findViewById(R.id.et_dialog_place);
		et_dialog_remark=(EditText)window.findViewById(R.id.et_dialog_remark);
		tv_dialog_add_log_ok=(TextView)window.findViewById(R.id.tv_dialog_add_log_ok);	
		tv_dialog_add_log_ok.setOnClickListener(this);	
		tv_dialog_add_log_cancel=(TextView)window.findViewById(R.id.tv_dialog_add_log_cancel);	
		tv_dialog_add_log_cancel.setOnClickListener(this);
		setValue(infor_name,infor_number);			
	}
	
	public void showDialog()
	{		
		dialog.show();
		Window window=dialog.getWindow();
		window.setContentView(R.layout.dialog_add_log);
	}
	
	public void setInfor(String infor_name,String infor_number)
	{
		if(infor_number.equals("新建联系人"))
		{
			infor_number="";
		}
		this.infor_name=infor_name;
		this.infor_number=infor_number;
	}
	
	public void setValue(String infor_name,String infor_number)
	{
		et_dialog_place.setText(infor_name);
		et_dialog_remark.setText(infor_number);		
	}	
	
	public void dismiss()
	{
		dialog.dismiss();
	}
	
	@Override
	public void onClick(View v) 
	{	
		listener.onClick(v);
	}	
	
	public  String getPlaceText()
	{
		String result = "";
		result = et_dialog_place.getText().toString();
		return result;		
	}
	
	public  String getRemarkText()
	{
		String result = "";
		result = et_dialog_remark.getText().toString();
		return result;		
	}
	
    public void showKeyboard()
    {  
        if(et_dialog_place!=null)
        {  
            //设置可获得焦点  
        	et_dialog_place.setFocusable(true);  
        	et_dialog_place.setFocusableInTouchMode(true);  
            //请求获得焦点  
        	et_dialog_place.requestFocus();  
            //调用系统输入法  
            InputMethodManager inputManager = (InputMethodManager)et_dialog_place
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
            inputManager.showSoftInput(et_dialog_place, 0);  
        }  
    }  
	
}
