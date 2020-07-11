package am.android.dialog;

import am.android.positionassistant.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MyDialogDeleteSMS implements OnClickListener 
{
	
	private AlertDialog     dialog           = null;
	private Context         context          = null;
	
	private OnClickListener listener         = null;
	
	private TextView        tv_sms_copy      = null;
	private TextView        tv_sms_delete    = null;
	private TextView        tv_sms_cancel    = null;
	

	public MyDialogDeleteSMS(Context context)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
	}
	
	public MyDialogDeleteSMS(Context context,OnClickListener listener)
	{		
		this.context = context;
		dialog = new AlertDialog.Builder(this.context).create();
		this.listener=listener;
	}
	
	
	public void show()
	{		
		dialog.show();		
		Window  window = dialog.getWindow();
		window.setContentView(R.layout.dialog_delete_sms);		
	   
		tv_sms_copy=(TextView)window.findViewById(R.id.tv_sms_copy);	
		tv_sms_copy.setOnClickListener(this);	
		tv_sms_delete=(TextView)window.findViewById(R.id.tv_sms_delete);	
		tv_sms_delete.setOnClickListener(this);	
		tv_sms_cancel=(TextView)window.findViewById(R.id.tv_sms_cancel);	
		tv_sms_cancel.setOnClickListener(this);	
		
	}
	
	
	public void showDialog()
	{
		dialog.show();
		Window window=dialog.getWindow();
		window.setContentView(R.layout.dialog_delete_sms);
	}
	
	public void dismiss()
	{
		dialog.dismiss();
	}
	
	@Override
	public void onClick(View view) 
	{
		listener.onClick(view);
	}

}
