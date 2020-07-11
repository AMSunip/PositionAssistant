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

public class MyDialogAccuracy implements OnClickListener
{	
	private AlertDialog       dialog                          = null;
	private Context           context                         = null;
	private OnClickListener   listener                        = null;
	private EditText          et_dialog_accuracy              = null;
	
	private LinearLayout      layout_dialog_accuracy          = null;
	private LinearLayout      layout_dialog_accuracy_divider1 = null;
	private LinearLayout      layout_dialog_accuracy_divider2 = null;
	
	private TextView          tv_dialog_custom_accuracy       = null;
	private TextView          tv_dialog_accuracy_ok           = null;
	private TextView          tv_dialog_accuracy_cancel       = null;
	private String            accuracy                        = null;
	
	private WindowManager     windowManager                   = null;
	private Display           diaplay                         = null;
	
	private boolean           isChanged                       = false; 
	private int               width                           = 810;
	
	public MyDialogAccuracy(Context context)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
	}
	
	public MyDialogAccuracy(Context context,OnClickListener listener)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
		this.listener=listener;
	}
	
	public MyDialogAccuracy(Context context,OnClickListener listener,String accuracy)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
		this.listener=listener;
		this.accuracy=accuracy;    	
	}
	
	public void setDialogTheme(String THEME)
	{
		if(THEME.equals("1"))
		{
			//炫酷黑
			layout_dialog_accuracy.setBackgroundResource(R.drawable.style_border_dialog2);
			tv_dialog_custom_accuracy.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			//tv_dialog_frequency_cancel.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			tv_dialog_accuracy_ok.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			et_dialog_accuracy.setTextColor(Consts.COLOR_THEME_BLACK_TEXT);
			layout_dialog_accuracy_divider1.setBackgroundResource(R.drawable.divider_black);
			layout_dialog_accuracy_divider2.setBackgroundResource(R.drawable.divider_black);
			
		}
		else
		{
			//简约白
			layout_dialog_accuracy.setBackgroundResource(R.drawable.style_border_dialog);
			tv_dialog_custom_accuracy.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			//tv_dialog_frequency_cancel.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			tv_dialog_accuracy_ok.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			et_dialog_accuracy.setTextColor(Consts.COLOR_THEME_DEFAULT_TEXT);
			layout_dialog_accuracy_divider1.setBackgroundResource(R.drawable.divider_default);
			layout_dialog_accuracy_divider2.setBackgroundResource(R.drawable.divider_default);
			
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
		window.setContentView(R.layout.dialog_accuracy);		
	   
		layout_dialog_accuracy=(LinearLayout)window.findViewById(R.id.layout_dialog_accuracy);
        layout_dialog_accuracy_divider1 =(LinearLayout)window.findViewById(R.id.layout_dialog_accuracy_divider1);
        layout_dialog_accuracy_divider2 =(LinearLayout)window.findViewById(R.id.layout_dialog_accuracy_divider2);
        
        tv_dialog_custom_accuracy=(TextView)window.findViewById(R.id.tv_dialog_custom_accuracy);
		et_dialog_accuracy=(EditText)window.findViewById(R.id.et_dialog_accuracy);
		
		tv_dialog_accuracy_ok=(TextView)window.findViewById(R.id.tv_dialog_accuracy_ok);	
		tv_dialog_accuracy_ok.setOnClickListener(this);	
		tv_dialog_accuracy_cancel=(TextView)window.findViewById(R.id.tv_dialog_accuracy_cancel);	
		tv_dialog_accuracy_cancel.setOnClickListener(this);
		setValue(accuracy);		
	}
		
	public void showDialog()
	{
		
		dialog.show();
		Window window=dialog.getWindow();
		window.setContentView(R.layout.dialog_accuracy);
	}
	
	public void setInfor(String accuracy)
	{
		this.accuracy=accuracy;
	}
	
	public void setValue(String accuracy)
	{
		et_dialog_accuracy.setText(accuracy);
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
	
	public  String getText()
	{
		String result = "";
		result = et_dialog_accuracy.getText().toString();
		return result;		
	}
	
    public void showKeyboard()
    {  
        if(et_dialog_accuracy!=null)
        {  
            //设置可获得焦点  
        	et_dialog_accuracy.setFocusable(true);  
        	et_dialog_accuracy.setFocusableInTouchMode(true);  
            //请求获得焦点  
        	et_dialog_accuracy.requestFocus();  
            //调用系统输入法  
            InputMethodManager inputManager = (InputMethodManager)et_dialog_accuracy
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
            inputManager.showSoftInput(et_dialog_accuracy, 0);  
        }  
    }  	
}
