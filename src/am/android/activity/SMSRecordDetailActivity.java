package am.android.activity;

import am.android.adapter.SMSInforDetailAdapter;
import am.android.consts.Consts;
import am.android.core.Launch;
import am.android.dialog.MyDialogDeleteSMS;
import am.android.manager.DatabaseManager;
import am.android.manager.InforManager;
import am.android.positionassistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class SMSRecordDetailActivity extends Activity implements OnClickListener
{
	
	private ClipboardManager      myClipboard                     = null;
	private ClipData              myClip                          = null;
	
	private ImageButton           imgbtn_comeback_smsrecorddetail = null;
	
	private ListView              listview_sms_record_detail      = null;
	
	private MyDialogDeleteSMS     mydialog_dsms                   = null;
	
	private SMSInforDetailAdapter smsinfordetailAdapter           = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_record_detail);
		
		init();
		
	}
	
	@SuppressLint("NewApi")
	private void copy(String text)
	{
		myClip = ClipData.newPlainText("text", text);
		myClipboard.setPrimaryClip(myClip);   
	}
	
	private void init()
	{
		initData();
		initView();
	}
	
	
	private void initData()
	{
		Launch.extractSMSInfor();
		smsinfordetailAdapter = new SMSInforDetailAdapter(SMSRecordDetailActivity.this, Consts.listdata_smsinfor_detail);
		
	}
	
	
	private void initView()
	{
		myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		
		imgbtn_comeback_smsrecorddetail = (ImageButton)findViewById(R.id.imgbtn_comeback_smsrecorddetail);
		imgbtn_comeback_smsrecorddetail.setOnClickListener(this);
		
		listview_sms_record_detail = (ListView)findViewById(R.id.listview_sms_record_detail);
		listview_sms_record_detail.setAdapter(smsinfordetailAdapter);
		
		listview_sms_record_detail.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
			{
				
				mydialog_dsms = new MyDialogDeleteSMS(SMSRecordDetailActivity.this, new OnClickListener() 
				{
					
					@Override
					public void onClick(View v)
					{
						
						if(v.getId() == R.id.tv_sms_copy)
						{
							int pos = Consts.listdata_smsinfor_detail.size() - position -1;
							copy(Consts.listdata_smsinfor_detail.get(pos).getInfor().trim());
							InforManager.showInfor(SMSRecordDetailActivity.this, "已复制当前坐标");
						}
						
						if(v.getId() == R.id.tv_sms_delete)
						{
							 int pos = Consts.listdata_smsinfor_detail.size() - position -1;
							 DatabaseManager.deleteItemSMSById(Consts.listdata_smsinfor_detail, pos);
								
							 DatabaseManager.getMySMSInfor();;
							 Launch.sortSMSInfor();
							 Launch.combineSMSInfor();
							 Launch.extractSMSInfor();
							 
							 smsinfordetailAdapter.notifyDataSetChanged();
							 
							 InforManager.showInfor(SMSRecordDetailActivity.this, "已删除该条记录");
							
						}
						
						if(v.getId() == R.id.tv_sms_cancel)
						{
							//取消
						}
						
						mydialog_dsms.dismiss();
					}
				});
				
				mydialog_dsms.show();
				
				return true;
			}
			
		});
		
		
		
	}


	
	@Override
	public void onClick(View view) 
	{
        if(view.getId() == imgbtn_comeback_smsrecorddetail.getId())
        {
        	finish();
        }

		
	}
	
	
}
